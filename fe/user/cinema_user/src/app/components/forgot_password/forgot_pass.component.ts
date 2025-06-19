import { Account, AccountUpdatePassword } from 'src/app/models/account.model';
import { AccountService } from './../../services/account.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription, interval } from 'rxjs';
import { MessageService } from 'primeng/api';

@Component({
  templateUrl: './forgot_pass.component.html',
  styleUrls: ['./forgot_pass.component.css']
})
export class ForgotPasswordComponent implements OnInit, OnDestroy{
  email: string = '';
  verificationCode: string = '';
  newPassword: string = '';
  isVerificationSent: boolean = false;
  verifyCode: string = '';
  expiredCode: Date;
  accountUpdated: Account;
  private countdownTime = 5 * 60 * 1000; // 5 phút
  timeLeft: string = '';
  private timerSubscription: Subscription;
    constructor(private accountService: AccountService, private router: Router, private messageService: MessageService) {

    }
  ngOnInit(): void {

  }
  ngOnDestroy() {
    if (this.timerSubscription) {
      this.timerSubscription.unsubscribe();
    }
  }



  // Gọi hàm gửi mã xác thực
  sendVerificationEmail(): void {
    this.isVerificationSent = true;
    let forgotRequest = {}
    if (this.email) {
      // Gửi email với mã xác thực (thực hiện gọi API ở đây)
      console.log(`Gửi mã xác thực đến ${this.email}`);
      this.accountService.findByEmail(this.email).then(
        (res) => {
          console.log('find email res:', res);
        },
        (err) => {
          this.messageService.add({
            severity: "error",
            summary: "Thất bại",
            detail: "Email không tồn tại. Vui lòng xác nhận lại."
          });
            this.router.navigate(['/forgot-password']);
        }
      )
    }
  }


  // Gọi hàm đặt lại mật khẩu
  resetPassword(): void {
    if (this.verificationCode && this.newPassword) {
        if(this.verificationCode === this.verifyCode && this.expiredCode && new Date() < this.expiredCode) {
            var account : Account = {
                id: this.accountUpdated.id,
                username: this.accountUpdated.username,
                password: this.newPassword,
                email: this.accountUpdated.email,
                phone: this.accountUpdated.phone,
                gender: this.accountUpdated.gender,
                birthday: this.accountUpdated.birthday,
                securitycode: this.accountUpdated.securitycode,
                verify: 1
            }
            this.accountService.update(account).then(
                (res) => {
                  this.messageService.add({
                    severity: "success",
                    summary: "Thành công",
                    detail: "Cập nhật mật khẩu thành công"
                  });
                    this.router.navigate(['/login']);
                },
                (err) => {
                  this.messageService.add({
                    severity: "error",
                    summary: "Thất bại",
                    detail: "Cập nhật mật khẩu thất bại"
                  });
                    this.router.navigate(['/login']);
                }
            )
        } else {
            alert("Cập nhật mật khẩu thất bại");
            this.router.navigate(['/login']);
        }
      // Xác thực mã và đặt lại mật khẩu (thực hiện gọi API ở đây)
      console.log(`Xác thực mã: ${this.verificationCode}`);
      console.log(`Đặt lại mật khẩu mới: ${this.newPassword}`);
      // Reset các trường
      this.email = '';
      this.verificationCode = '';
      this.newPassword = '';
      this.isVerificationSent = false;
    }
  }


}

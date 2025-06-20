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
          const resetPasswordRequest = {
            "email": res.data.email,
            "userId": res.data.userId
          };

          console.log('reset pass: ', resetPasswordRequest)

          this.accountService.forgotPassword(resetPasswordRequest).then(
            (res) => {
              console.log('forgot hoàn thành')
            }
          )
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
}

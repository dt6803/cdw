import { OnInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AccountService } from 'src/app/services/account.service';



@Component({
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {

  constructor(
    private accountService: AccountService,
    private router: Router,
    private messageService: MessageService
    ) {}
    username: string;
    password: string;
  ngOnInit(): void {

   
  }
  login(){
   
    var account = {
      email: this.username,
      password: this.password
    }
      this.accountService.login(account).then(
        res => {
          console.log(res);
          if(res.status == true && res.role == 0){
             localStorage.setItem("username", this.username);
              this.router.navigate(['/admin/dashboard']);
              console.log(this.accountService.isLogin());
            
          } else if(res.status == false){
            this.messageService.add({
              severity: 'error',
              summary: 'Đăng nhập thất bại',
              detail: 'Tài khoản hoặc mật khẩu sai!',
            });
          }
          err => {
            this.messageService.add({
              severity: 'error',
              summary: 'Đăng nhập thất bại',
              detail: 'Tài khoản hoặc mật khẩu sai!',
            });
          }
        } 
      );
     
    // localStorage.setItem("username", "acc1");
    // this.router.navigate(['/admin/dashboard']);
    // console.log(this.accountService.isLogin());
  }
}

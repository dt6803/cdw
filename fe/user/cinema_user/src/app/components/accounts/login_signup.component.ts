import { AccountService } from './../../services/account.service';
import { ChangeDetectorRef, Component, ElementRef, OnInit, AfterViewInit  } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { MessageService } from 'primeng/api';
import { Account, AccountLogin } from 'src/app/models/account.model';
import { AuthService } from 'src/app/services/auth.service';
import {jwtDecode} from 'jwt-decode';
import {AppComponent} from "../../app.component";
declare let google: any;
// Các trường dữ liệu sau khi mã hóa jwt thì sẽ cho ra các thông tin
interface DecodedToken {
  sub: string;
  email: string;
  name: string;
}
@Component({
  templateUrl: './login_signup.component.html',
  styleUrls: ['./login_signup.component.css'],
})
export class Login_SignupComponent implements OnInit, AfterViewInit  {
  signupForm: FormGroup;
  loginForm: FormGroup;
  randomNumber = Math.floor(100000 + Math.random() * 900000);
  newAccount: any;
  account: Account;
  authenticatedAccount: boolean;

  constructor(
    private el: ElementRef,
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private router: Router,
    private cdr: ChangeDetectorRef,
    private messageService: MessageService,
    private authService: AuthService,
    private appComponent: AppComponent,
  ) {
    this.signupForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      fullname: ['', Validators.required],
      birthday: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
    });

    this.loginForm = this.formBuilder.group({
      usernameLogin: ['', [Validators.required]],
      passwordLogin: ['', Validators.required],
    })
  }
  ngAfterViewInit(): void {

  }

  async signUp(): Promise<void> {
    // Kiểm tra mật khẩu xác nhận
    if (this.signupForm.value.password !== this.signupForm.value.confirmPassword) {
      this.messageService.add({
        severity: "error",
        summary: "Xác nhận lại mật khẩu",
        detail: "Mật khẩu xác nhận không trùng với mật khẩu bạn tạo. Vui lòng nhập lại"
      });
      console.log(1);
      return; // Ngăn không cho tiếp tục nếu mật khẩu không khớp
    }

    // Kiểm tra tính hợp lệ của form
    // if (!this.signupForm.valid) {
    //   this.messageService.add({
    //     severity: "error",
    //     summary: "Đăng kí thất bại",
    //     detail: "Vui lòng nhập đầy đủ thông tin"
    //   });
    //   console.log(2);
    //   return; // Ngăn không cho tiếp tục nếu biểu mẫu không hợp lệ
    // }

    // Định dạng ngày sinh
    const birthday = this.signupForm.value.birthday;
    console.log(birthday);
    const formattedBirthday = moment(birthday).format('YYYY-MM-DD');
    console.log(formattedBirthday);
    // Khởi tạo đối tượng newAccount
    this.newAccount = {
      username: this.signupForm.value.username,
      password: this.signupForm.value.password,
      email: this.signupForm.value.email,
      fullName: this.signupForm.value.fullname,
      dob: formattedBirthday,
      phoneNumber: this.signupForm.value.phone,
    };

    console.log('form info: ', this.newAccount)
    this.accountService.signUp(this.newAccount).then(
      (res) => {
        console.log('regis: ', res)
        if (res.status === 'Success') {
          this.router.navigate(['/home']);
        } else {
          this.messageService.add({
            severity: "error",
            summary: "Đăng ký thất bại",
            detail: "Có lỗi trong quá trình đăng ký, vui loòng thử lại"
          });
        }
      }
    );

  }

  async login(): Promise<void> {
    console.log(this.loginForm)
    if (!this.loginForm.valid) {
      this.messageService.add({
        severity: "error",
        summary: "Đăng nhập thất bại",
        detail: "Bạn vui lòng nhập đầy đủ thông tin"
      });
      return; // Ngăn không cho tiếp tục nếu biểu mẫu không hợp lệ
    }

    const loginAccount: AccountLogin = {
      username: this.loginForm.value.usernameLogin,
      password: this.loginForm.value.passwordLogin,
    };
    console.log('accournt request: ', loginAccount)

    try {
      const res = await this.accountService.login(loginAccount);

      if (res.status == 'Success' && res.data.status == 'Success') {
        localStorage.setItem('accessToken', res.data.accessToken);
        localStorage.setItem('refreshToken', res.data.refreshToken);
        this.appComponent.getCurrentUser();
        this.messageService.add({
          severity: "success",
          summary: "Đăng nhập thành công",
          detail: "Đăng nhập thành công"
        });

        // Redirect to home page after a brief delay
         this.router.navigate(['/']);
      } else {
        this.messageService.add({
          severity: "error",
          summary: "Đăng nhập thất bại",
          detail: "Đăng nhập không thành công. Vui lòng kiểm tra thông tin đăng nhập của bạn."
        });

        // Redirect to login page after a brief delay
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 4000);
      }
    } catch (error) {
      console.error('Login error:', error);
      this.messageService.add({
        severity: "error",
        summary: "Đăng nhập thất bại",
        detail: "Đăng nhập không thành công. Vui lòng kiểm tra thông tin đăng nhập của bạn."
      });

      // Redirect to login page after a brief delay
      setTimeout(() => {
        this.router.navigate(['/login']);
      }, 4000);
    }
  }


  ngOnInit(): void {
    // Thực hiện các thao tác khởi tạo khi component được tạo

  }

  togglePasswordVisibility(event: Event): void {
    const eyeIcon = event.target as HTMLElement;
    const pwFieldContainer = eyeIcon.closest('.input-field');
    const pwFields = pwFieldContainer?.querySelectorAll('.password');

    if (pwFields) {
      pwFields.forEach((passwordField: HTMLInputElement) => {
        if (passwordField.type === 'password') {
          passwordField.type = 'text';
          eyeIcon.classList.replace('bx-hide', 'bx-show');
        } else {
          passwordField.type = 'password';
          eyeIcon.classList.replace('bx-show', 'bx-hide');
        }
      });
    }
  }

  toggleSignUp(event: Event): void {
    event.preventDefault(); // Ngăn chặn việc gửi biểu mẫu
    const forms = this.el.nativeElement.querySelector('.forms');
    forms.classList.toggle('show-signup');
  }
}

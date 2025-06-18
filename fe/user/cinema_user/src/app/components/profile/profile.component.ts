import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Account, AccountLogin} from "src/app/models/account.model";
import * as moment from 'moment';
import {AccountService} from "src/app/services/account.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {jwtDecode} from "jwt-decode";
import {BookingService} from "../../services/booking.service";

@Component({
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  updateForm: FormGroup;
  showCurrentPassword: boolean = false;
  showNewPassword: boolean = false;
  showConfirmPassword: boolean = false;
  userId: string;
  activeTab: 'info' | 'history' = 'info';
  tickets = [];
  account: Account;
  profile: any;
  isLoading = false;
  selectedTicket: any;
  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private router: Router,
    private messageService: MessageService,
    private bookingService: BookingService,

  ) {
    this.updateForm = this.fb.group({
      username: ['', Validators.required],
      currentPassword: [''],
      newPassword: [''],
      confirmPassword: [''],
      email: ['', [Validators.required, Validators.email]],
      birthday: ['', Validators.required],
      phone: ['', Validators.required],
      gender: ['', Validators.required],
    }, {validator: this.matchPassword('newPassword', 'confirmPassword')});
  }

  ngOnInit(): void {
    const token = localStorage.getItem('accessToken');

    if (!token) {
      this.router.navigate(['/']);
      return;
    }

    try {
      const decoded: any = jwtDecode(token);
      this.userId = decoded.sub;
      // Lấy thông tin user
      this.accountService.getProfileByUserId(this.userId).then(
        (res) => {
          this.profile = res.data;
          console.log("profile: ", this.profile)
        }
      );

      //Lấy danh sách vé đã mua
      this.isLoading = true;
      this.bookingService.getAllByUserId(this.userId)
        .then((res) => {
          this.tickets = res.data;
          console.log("tickets: ", this.tickets);
        })
        .catch((error) => {
          console.error('Lỗi khi tải vé:', error);
          // có thể hiển thị thông báo lỗi cho người dùng nếu cần
        })
        .finally(() => {
          this.isLoading = false;
        });



    } catch (error) {
      console.error('Token không hợp lệ:', error);
      this.router.navigate(['/']);
    }
  }

  showTicket(id: string) {
    this.bookingService.getById(id).then(
      (res) => {
        this.selectedTicket = res.data
        console.log('selected: ', this.selectedTicket)
      }
    );
  }


  togglePasswordVisibility(field: string) {
    if (field === 'currentPassword') {
      this.showCurrentPassword = !this.showCurrentPassword;
    } else if (field === 'newPassword') {
      this.showNewPassword = !this.showNewPassword;
    } else if (field === 'confirmPassword') {
      this.showConfirmPassword = !this.showConfirmPassword;
    }
  }

  logout() {

  }

  private matchPassword(passwordField: string, confirmPasswordField: string) {
    return (formGroup: FormGroup) => {
      const password = formGroup.get(passwordField)?.value;
      const confirmPassword = formGroup.get(confirmPasswordField)?.value;

      if (password !== confirmPassword) {
        formGroup.get(confirmPasswordField)?.setErrors({mismatch: true});
      } else {
        formGroup.get(confirmPasswordField)?.setErrors(null);
      }
    };
  }
}

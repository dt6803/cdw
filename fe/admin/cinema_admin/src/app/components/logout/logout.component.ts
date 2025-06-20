// logout.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  template: ''
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Xóa token (hoặc bất kỳ dữ liệu nào khác)
    localStorage.removeItem('accessToken');

    // Kiểm tra nếu đang logout đồng bộ
    const params = new URLSearchParams(window.location.search);
    const sync = params.get('sync');
    console.log('logut')
    if (sync === 'true') {
      // Logout đồng bộ: quay lại app chính hoặc login
      window.location.href = 'http://localhost:4200/home';
    } else {
      // Logout bình thường: chuyển về trang login admin
      this.router.navigate(['/']);
    }
  }
}

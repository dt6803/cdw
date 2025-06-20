import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {jwtDecode} from "jwt-decode";

// @ts-ignore
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'cinema_admin';
  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    console.log('App khởi động!');
    // 1. Nhận token từ query param nếu có
    this.route.queryParams.subscribe(params => {
      const tokenFromUrl = params['token'];
      console.log(tokenFromUrl)
      if (tokenFromUrl) {
        localStorage.setItem('accessToken', tokenFromUrl);

        //2. Xoá token khỏi URL cho sạch
        this.router.navigate([], {
          queryParams: {},
          replaceUrl: true
        });
      }

      // 3. Kiểm tra token trong localStorage
      const token = localStorage.getItem('accessToken');

      if (token) {
        try {
          const payload: any = jwtDecode(token);
          console.log('payload: ', payload)
          if (payload?.iss !== 'admin') {
            // Nếu không phải admin → chuyển hướng đến trang không có quyền
            this.router.navigate(['/login']);
            console.log('sai role')
          }
        } catch (err) {
          // Token không hợp lệ → cũng chuyển hướng
          this.router.navigate(['/login']);
        }
      } else {
        // Không có token → cũng không cho vào
        this.router.navigate(['/login']);
      }
      console.log('đăng nhập đcượ')
      this.router.navigate(['/admin/dashboard']);
    });

  }
}

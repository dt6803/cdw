// token-redirect.component.ts
import {ActivatedRoute, Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {jwtDecode} from "jwt-decode";

@Component({
  selector: 'app-token-redirect',
  template: '<p>Đang xác thực...</p>',
})
export class TokenRedirectComponent implements OnInit {
  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    const token = this.route.snapshot.queryParamMap.get('token');
    console.log('Token nhận được:', token);
    if (token) {
      try {
        const payload: any = jwtDecode(token);
        console.log('Payload:', payload);

        if (payload?.iss === 'admin') {
          localStorage.setItem('accessToken', token);
          this.router.navigate(['/admin/dashboard'], { replaceUrl: true });
        } else {
          console.warn('Token không có quyền admin');
          this.router.navigate(['/login'], { replaceUrl: true });
        }
      } catch (err) {
        console.error('Token không hợp lệ:', err);
        this.router.navigate(['/login'], { replaceUrl: true });
      }
    } else {
      console.warn('Không tìm thấy token trong URL');
      this.router.navigate(['/login'], { replaceUrl: true });
    }
  }
}

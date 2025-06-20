import { Injectable } from '@angular/core';
import { CanActivate, Router } from "@angular/router";
import { AccountService } from './account.service';
import {jwtDecode} from "jwt-decode";
@Injectable()
export class CheckLoginService implements CanActivate{
    constructor(
        private accountService: AccountService,
        private router: Router
    ){}
    canActivate(){
      const token = localStorage.getItem('accessToken');

      if (token) {
        try {
          const payload: any = jwtDecode(token);
          if (payload?.iss === 'admin') {
            return true;
          }
        } catch (err) {
          // Token lỗi
        }
      }

      // ❌ Không hợp lệ → chuyển về login
      this.router.navigate(['/login']);
      return false;

    }
}

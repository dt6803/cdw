import { Injectable } from '@angular/core';
import { CanActivate, Router } from "@angular/router";
import { AccountService } from './account.service';
@Injectable()
export class CheckLoginService implements CanActivate{
    constructor(
        private accountService: AccountService,
        private router: Router
    ){}
    canActivate(){
        var username = localStorage.getItem("username");
        console.log(username);
        if(username != null){
            return true;
        } else{
            this.router.navigate(['/access-denied']);
           return false;
        }
     
    }
}
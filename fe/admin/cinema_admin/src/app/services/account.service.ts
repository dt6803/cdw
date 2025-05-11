import { Injectable } from '@angular/core';
import { BaseUrlService } from './baseUrl.service';
import { HttpClient } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
@Injectable()
export class AccountService{
    public status: boolean = false;
    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient
    ) { }
    accountService(_status: boolean){
        this.status = _status;
    }

    isLogin(){
        return this.status;
    }
    async findAll() : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'account/findAll'));
    }
    async login(account: any) : Promise<any>{
        return await lastValueFrom(this .httpClient.post(this.baseUrlService.getBaseUrl()
        + 'account/login', account));
    }
}
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {BaseUrlService} from "./baseUrl.service";
import {Account, AccountLogin} from "../models/account.model";
import {BehaviorSubject, lastValueFrom} from "rxjs";

@Injectable()
export class AccountService {
  private accountSubject = new BehaviorSubject<any>(null);

  constructor(
    private baseUrlService: BaseUrlService,
    private httpClient: HttpClient
  ) {
  }

  async signUp(request: any): Promise<any> {
    return await lastValueFrom(this.httpClient.post(this.baseUrlService.getBaseUrl()
      + 'authentication/users/registration', request));
  }


  async login(account: AccountLogin): Promise<any> {
    console.log('request: ', account)
    return await lastValueFrom(this.httpClient.post(this.baseUrlService.getBaseUrl()
      + 'authentication/auth/login', account));
  }


  async forgotPassword(request: any): Promise<any> {
    return await lastValueFrom(this.httpClient.post(this.baseUrlService.getBaseUrl()
      + 'authentication/auth/forgot-password', request));
  }

  async update(account: Account): Promise<any> {
    return await lastValueFrom(this.httpClient.put(this.baseUrlService.getBaseUrl()
      + 'account/update', account));
  }

  async findAccountById(id: number): Promise<any> {
    return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
      + 'authentication/users/me' + id));
  }

  async getProfileByUserId(id: string): Promise<any> {
    return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
      + 'profile/users/' + id));
  }

  async getMyAccountInfo(): Promise<any> {
    return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
      + 'authentication/users/me'));
  }

  async verifyAccount(email: string): Promise<any> {
    // Đảm bảo email được mã hóa đúng
    const encodedEmail = encodeURIComponent(email);

    // Gọi API và trả về kết quả dưới dạng Promise
    return await lastValueFrom(this.httpClient.get<any>(`${this.baseUrlService.getBaseUrl()}account/verify?email=${encodedEmail}`));
  }

  async findByEmail(email: string): Promise<any> {
    return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
      + 'profile/users/findEmail/' + email));
  }

  async findByUsername(username: string): Promise<any> {
    return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
      + 'account/findByUsername/' + username));
  }

  setAccount(account: any) {
    localStorage.setItem('account', JSON.stringify(account));
    this.accountSubject.next(account);
  }

  getAccount() {
    return this.accountSubject.asObservable();
  }
}

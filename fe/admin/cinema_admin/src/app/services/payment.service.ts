import { Injectable } from "@angular/core";
import { BaseUrlService } from "./baseUrl.service";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";
import { DatePipe } from "@angular/common";

@Injectable()
export class PaymentService{
    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient,
        private datePipe: DatePipe,
    ){}
    async findAll() : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'payment/findAll'));
    }

}
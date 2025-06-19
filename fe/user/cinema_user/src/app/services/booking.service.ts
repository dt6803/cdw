import { Injectable } from "@angular/core";
import { BaseUrlService } from "./baseUrl.service";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";
import { BookingRequest} from "../models/booking.model";

@Injectable()
export class BookingService{
    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient
    ){}
    async create(booking: BookingRequest) : Promise<any>{
        return await lastValueFrom(this .httpClient.post(this.baseUrlService.getBaseUrl()
        + 'booking/bookings', booking));
    }

  async getById(id: string ) : Promise<any>{
    return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
      + 'booking/bookings/' + id));
  }

  async getAllByUserId(id: string ) : Promise<any>{
    return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
      + 'booking/bookings/user/' + id));
  }

  async sendMailConfirm(booking: any) : Promise<any>{
    return await lastValueFrom(this .httpClient.post(this.baseUrlService.getBaseUrl()
      + 'notification/email/send', booking));
  }

}

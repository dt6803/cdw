import { Injectable } from "@angular/core";
import { BaseUrlService } from "./baseUrl.service";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";

@Injectable()
export class ShowTimeService{
    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient
    ){}
    async findById(id: string) : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'showtime/showtimes/'+ id));
    }
    async getSeatLayout(id: String) : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'cinema/rooms/'+ id + '/seats/layout'));
    }
    async findMovie(date: string, cinemaId: number, movieId: number) : Promise<any>{
        return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
        + 'showTime/findMovie?date='+ date + '&cinemaId=' + cinemaId + '&movieId=' + movieId));
    }
}

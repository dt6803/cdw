import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BaseUrlService } from "./baseUrl.service";
import { lastValueFrom } from "rxjs";

@Injectable()
export class ShowtimeService{

    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient
    ){}
    async findAll() : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'showtime/showtimes'));
    }

    async findById(id: number) : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'showtime/findById/' + id));
    }

    async findAllByCinema(cinemaId: string) : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'showtime/findAllByCinema/' + cinemaId));
    }
    async create(showtime: any) : Promise<any>{
        return await lastValueFrom(this .httpClient.post(this.baseUrlService.getBaseUrl()
        + 'showtime/create', showtime));
    }
    async edit(showtime: any) : Promise<any>{
        return await lastValueFrom(this .httpClient.put(this.baseUrlService.getBaseUrl()
        + 'showtime/edit', showtime));
    }
    async delete(id: string) : Promise<any>{
        return await lastValueFrom(this .httpClient.delete(this.baseUrlService.getBaseUrl()
        + 'showtime/delete/' + id));
    }

}

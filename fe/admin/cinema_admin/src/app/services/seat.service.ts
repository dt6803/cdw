import { Injectable } from "@angular/core";
import { BaseUrlService } from "./baseUrl.service";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";

@Injectable()
export class SeatService{
    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient
    ){}
    async findAll() : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'seat/findAll'));
    }
    async create(seat: any) : Promise<any>{
        return await lastValueFrom(this .httpClient.post(this.baseUrlService.getBaseUrl()
        + 'seat/create', seat));
    }
    async delete(id: number) : Promise<any>{
        return await lastValueFrom(this .httpClient.delete(this.baseUrlService.getBaseUrl()
        + 'seat/delete/' + id));
    }
    async edit(seat: any) : Promise<any>{
        return await lastValueFrom(this .httpClient.put(this.baseUrlService.getBaseUrl()
        + 'seat/edit', seat));
    }
    async findById(id: number) : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'seat/findById/' + id));
    }


   
}
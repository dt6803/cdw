import { Injectable } from "@angular/core";
import { BaseUrlService } from "./baseUrl.service";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";
import { DatePipe } from "@angular/common";

@Injectable()
export class MovieService{
    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient,
        private datePipe: DatePipe,
    ){}
    async findAll() : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'movie/findAllByStatus'));
    }
    async create(form: FormData) : Promise<any>{
        return await lastValueFrom(this .httpClient.post(this.baseUrlService.getBaseUrl()
        + 'movie/create', form));
    }
    async delete(id: number) : Promise<any>{
        return await lastValueFrom(this .httpClient.delete(this.baseUrlService.getBaseUrl()
        + 'movie/delete/' + id));
    }
    async edit(movie: any) : Promise<any>{
        return await lastValueFrom(this .httpClient.put(this.baseUrlService.getBaseUrl()
        + 'movie/edit', movie));
    }
    async findById(id: number) : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'movie/findMovieById/' + id));
    }

}
import { Injectable } from "@angular/core";
import { BaseUrlService } from "./baseUrl.service";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";

@Injectable()
export class MovieService{
    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient
    ){}
    async findAll(date: string, id: string) : Promise<any>{
        return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
        + 'showtime/showtimes/findAll/by?date='+ date.split(' ')[0] + '&cinemaId=' + id));
    }

    async findAllByStatus() : Promise<any>{
        return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
        + 'movie/movies'));
    }

    async findMovieById(id: string) : Promise<any>{
        return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
        + 'movie/movies/' + id));
    }

    async findShowtimesByMovieId(date: string, cinemaId: string, movieId: string) : Promise<any>{
        return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
        + 'showtime/showtimes/findAll/byMovie?date='+ date + '&cinemaId=' + cinemaId + '&movieId=' + movieId));
    }
}

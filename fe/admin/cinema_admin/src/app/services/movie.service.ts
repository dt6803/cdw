import {Injectable} from "@angular/core";
import {BaseUrlService} from "./baseUrl.service";
import {HttpClient} from "@angular/common/http";
import {lastValueFrom} from "rxjs";
import {DatePipe} from "@angular/common";

@Injectable()
export class MovieService {
  constructor(
    private baseUrlService: BaseUrlService,
    private httpClient: HttpClient,
    private datePipe: DatePipe,
  ) {
  }

  async findAll(): Promise<any> {
    return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
      + 'movie/movies'));
  }

  async create(form: FormData): Promise<any> {
    return await lastValueFrom(this.httpClient.post(this.baseUrlService.getBaseUrl()
      + 'movie/create', form));
  }


  async createMovie(request: any): Promise<any> {
    return await lastValueFrom(this.httpClient.post(this.baseUrlService.getBaseUrl()
      + 'movie/movies', request));
  }

  async delete(id: number): Promise<any> {
    return await lastValueFrom(this.httpClient.delete(this.baseUrlService.getBaseUrl()
      + 'movie/delete/' + id));
  }

  async updateMovie(id: string, movie: any): Promise<any> {
    return await lastValueFrom(this.httpClient.put(this.baseUrlService.getBaseUrl()
      + 'movie/movies/' + id, movie));
  }

  async findById(id: number): Promise<any> {
    return await lastValueFrom(this.httpClient.get(this.baseUrlService.getBaseUrl()
      + 'movie/findMovieById/' + id));
  }

}

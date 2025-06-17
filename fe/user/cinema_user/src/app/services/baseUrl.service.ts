import { Injectable } from "@angular/core";

@Injectable()
export class BaseUrlService{
    private BaseUrl: string = "http://localhost:8888/api/v1/";
    getBaseUrl(): string {
        return this.BaseUrl;
    }

}

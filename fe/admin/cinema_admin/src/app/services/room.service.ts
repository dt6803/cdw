import { Injectable } from "@angular/core";
import { BaseUrlService } from "./baseUrl.service";
import { HttpClient } from "@angular/common/http";
import { lastValueFrom } from "rxjs";

@Injectable()
export class RoomService{
    constructor(
        private baseUrlService: BaseUrlService,
        private httpClient: HttpClient
    ){}
    async findAll() : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'room/findAll'));
    }
    async createRoom(room: any) : Promise<any>{
        return await lastValueFrom(this .httpClient.post(this.baseUrlService.getBaseUrl()
        + 'cinema/rooms', room));
    }
  async createSeatLayout(id: string, request: any) : Promise<any>{
    return await lastValueFrom(this .httpClient.post(this.baseUrlService.getBaseUrl()
      + 'cinema/rooms/' + id + '/seats/layout', request));
  }

  async updateRoom(id: string, request: any) : Promise<any>{
    return await lastValueFrom(this .httpClient.put(this.baseUrlService.getBaseUrl()
      + 'cinema/rooms/' + id, request));
  }


    async delete(id: number) : Promise<any>{
        return await lastValueFrom(this .httpClient.delete(this.baseUrlService.getBaseUrl()
        + 'room/delete/' + id));
    }
    async edit(room: any) : Promise<any>{
        return await lastValueFrom(this .httpClient.put(this.baseUrlService.getBaseUrl()
        + 'room/edit', room));
    }
    async findById(id: string) : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'cinema/rooms/' + id));
    }
    async findByCinemaId(id: string) : Promise<any>{
        return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
        + 'room/findByCinemaId/' + id));
    }

  async getSeatLayout(id: string) : Promise<any>{
    return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
      + 'cinema/rooms/'+ id +'/seats/layout'));
  }





  async findRoomInfoByCinemaId(id: string) : Promise<any>{
    return await lastValueFrom(this .httpClient.get(this.baseUrlService.getBaseUrl()
      + 'cinema/cinemas/' + id + '/rooms'));
  }

}

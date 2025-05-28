import { Injectable } from '@angular/core';
import { BaseUrlService } from './baseUrl.service';
import { HttpClient } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';

@Injectable()
export class ComboService {
  constructor(
    private baseUrlService: BaseUrlService,
    private httpClient: HttpClient
  ) {}
  async findAll(): Promise<any> {
    return await lastValueFrom(
      this.httpClient.get(this.baseUrlService.getBaseUrl() + 'combo/findAll')
    );
  }
  async create(combo: any): Promise<any> {
    return await lastValueFrom(
      this.httpClient.post(
        this.baseUrlService.getBaseUrl() + 'combo/create',
        combo
      )
    );
  }
  async delete(id: number): Promise<any> {
    return await lastValueFrom(
      this.httpClient.delete(
        this.baseUrlService.getBaseUrl() + 'combo/delete/' + id
      )
    );
  }
  async edit(combo: any): Promise<any> {
    return await lastValueFrom(
      this.httpClient.put(
        this.baseUrlService.getBaseUrl() + 'combo/edit',
        combo
      )
    );
  }
  async findById(id: number): Promise<any> {
    return await lastValueFrom(
      this.httpClient.get(
        this.baseUrlService.getBaseUrl() + 'combo/findById/' + id
      )
    );
  }
}

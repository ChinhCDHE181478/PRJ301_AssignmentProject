import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { Campaign } from '../dto/campaign';

@Injectable({
  providedIn: 'root'
})
export class CampaignService {

  private apiUrlAll = `${environment.apiBaseUrl}/campaigns/all`;
  private apiUrlSearch = `${environment.apiBaseUrl}/campaigns/search`;
  private apiUrlCreate = `${environment.apiBaseUrl}/campaigns/create`;
  private apiUrlUpdate = `${environment.apiBaseUrl}/campaigns/update`;
  private apiUrlDelete = `${environment.apiBaseUrl}/campaigns/delete`;

  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) { }

  all(): Observable<Campaign[]> {
    return this.http.get<Campaign[]>(this.apiUrlAll, this.apiConfig);
  }

  search(camp: Campaign): Observable<Campaign[]> {

    let params = new HttpParams();

    if(camp.campaignId) {
      params = params.set("campId", camp.campaignId);
    }

    if(camp.planId) {
      params = params.set("planId", camp.planId);
    }

    if(camp.product?.productId) {
      params = params.set("prodId", camp.product.productId);
    }

    return this.http.get<Campaign[]>(this.apiUrlSearch, {params, ...this.apiConfig});
  }

  create(camp: Campaign): Observable<any> {
    return this.http.post<any>(this.apiUrlCreate, camp, this.apiConfig);
  }

  update(camp: Campaign): Observable<any> {
    return this.http.put<any>(this.apiUrlUpdate, camp, this.apiConfig);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrlDelete}/${id}`, this.apiConfig);
  }

}

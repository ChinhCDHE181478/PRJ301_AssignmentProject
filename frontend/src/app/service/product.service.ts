import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { Product } from '../dto/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrlAll = `${environment.apiBaseUrl}/products/all`;

  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) { }

  all(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrlAll, this.apiConfig);
  }
}

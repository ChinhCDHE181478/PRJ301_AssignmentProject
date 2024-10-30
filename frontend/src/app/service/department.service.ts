import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { Department } from '../dto/department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private apiUrlAll = `${environment.apiBaseUrl}/departments/all`;

  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) { }

  all(): Observable<Department[]> {
    return this.http.get<Department[]>(this.apiUrlAll, this.apiConfig);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { Attendent } from '../dto/attendent';

@Injectable({
  providedIn: 'root'
})
export class AttendentService {

  private apiUrlCreate = `${environment.apiBaseUrl}/attendents/create`;
  private apiUrlUpdate = `${environment.apiBaseUrl}/attendents/update`;
  private apiUrlAll = `${environment.apiBaseUrl}/attendents/all`;
  private apiUrlSearch = `${environment.apiBaseUrl}/attendents/search`;
  private apiUrlDelete = `${environment.apiBaseUrl}/attendents/delete`;

  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) { }

  all(): Observable<Attendent[]> {
    return this.http.get<Attendent[]>(this.apiUrlAll, this.apiConfig);
  }

  search(attendent: Attendent): Observable<Attendent[]> {
    let params = new HttpParams();

    if(attendent.worker.workerId) {
      params = params.set("wId", attendent.worker.workerId);
    }

    if(attendent.worker.scheduleId) {
      params = params.set("sId", attendent.worker.scheduleId);
    }

    if(attendent.worker.employee.employeeId) {
      params = params.set("eId", attendent.worker.employee.employeeId);
    }

    return this.http.get<Attendent[]>(this.apiUrlSearch, {params, ...this.apiConfig});
  }

  create(attendent: Attendent): Observable<any> {
    return this.http.post<any>(this.apiUrlCreate, attendent, this.apiConfig);
  }

  update(attendent: Attendent): Observable<any> {
    return this.http.put<any>(this.apiUrlUpdate, attendent, this.apiConfig);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrlDelete}/${id}`, this.apiConfig);
  }
}

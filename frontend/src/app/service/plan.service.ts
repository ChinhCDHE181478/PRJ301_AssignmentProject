import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { Plan } from '../dto/plan';

@Injectable({
  providedIn: 'root',
})
export class PlanService {
  private apiUrlCreate = `${environment.apiBaseUrl}/production/plans/create`;
  private apiUrlUpdate = `${environment.apiBaseUrl}/production/plans/update`;
  private apiUrlAll = `${environment.apiBaseUrl}/production/plans/all`;
  private apiUrlSearch = `${environment.apiBaseUrl}/production/plans/search`;
  private apiUrlDelete = `${environment.apiBaseUrl}/production/plans/delete`;

  private apiConfig = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient) {}

  create(plan: Plan): Observable<any> {
    return this.http.post<any>(this.apiUrlCreate, plan, this.apiConfig);
  }

  update(plan: Plan): Observable<any> {
    return this.http.put<any>(this.apiUrlUpdate, plan, this.apiConfig);
  }

  all(): Observable<Plan[]> {
    return this.http.get<Plan[]>(this.apiUrlAll, this.apiConfig);
  }

  search(plan: Plan): Observable<Plan[]> {
    let params = new HttpParams();

    if (plan.startDate) {
      params = params.set('from', plan.startDate.toString());
    }

    if (plan.endDate) {
      params = params.set('from', plan.endDate.toString());
    }

    if(plan.department?.departmentId) {
      params = params.set("dId", plan.department.departmentId);
    }

    return this.http.get<Plan[]>(this.apiUrlSearch, {params, ...this.apiConfig});
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrlDelete}/${id}`, this.apiConfig);
  }
}

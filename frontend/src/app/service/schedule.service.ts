import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment/environment';
import { Observable } from 'rxjs';
import { Schedule } from '../dto/schedule';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
  private apiUrlAll = `${environment.apiBaseUrl}/schedules/all`;
  private apiUrlSearch = `${environment.apiBaseUrl}/schedules/search`;
  private apiUrlCreate = `${environment.apiBaseUrl}/schedules/create`;
  private apiUrlUpdate = `${environment.apiBaseUrl}/schedules/update`;
  private apiUrlDelete = `${environment.apiBaseUrl}/schedules/delete`;

  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) {}

  all(): Observable<Schedule[]> {
    return this.http.get<Schedule[]>(this.apiUrlAll, this.apiConfig);
  }

  search(schedule: Schedule): Observable<Schedule[]> {
    let params = new HttpParams();

    if(schedule.scheduleId) {
      params = params.set("scheduleId", schedule.scheduleId);
    }

    if(schedule.campaignId) {
      params = params.set("campId", schedule.campaignId);
    }

    if(schedule.date) {
      params = params.set("date", schedule.date.toString());
    }

    if(schedule.shift?.shiftId) {
      params = params.set("shiftId", schedule.shift.shiftId);
    }

    return this.http.get<Schedule[]>(this.apiUrlSearch, {params, ...this.apiConfig});
  }

  create(schedule: Schedule): Observable<any> {
    return this.http.post<any>(this.apiUrlCreate, schedule, this.apiConfig);
  }

  update(schedule: Schedule): Observable<any> {
    return this.http.put<any>(this.apiUrlUpdate, schedule, this.apiConfig);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrlDelete}/${id}`, this.apiConfig);
  }
}

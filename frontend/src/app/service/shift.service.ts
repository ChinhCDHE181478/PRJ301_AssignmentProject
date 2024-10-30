import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment/environment';
import { Observable } from 'rxjs';
import { Shift } from '../dto/shift';

@Injectable({
  providedIn: 'root'
})
export class ShiftService {

  private apiUrlAll = `${environment.apiBaseUrl}/shifts/all`;

  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) {}

  all(): Observable<Shift[]> {
    return this.http.get<Shift[]>(this.apiUrlAll, this.apiConfig);
  }
}

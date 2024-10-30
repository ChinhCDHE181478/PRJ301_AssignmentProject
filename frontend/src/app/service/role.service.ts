import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment/environment';
import { Observable } from 'rxjs';
import { Role } from '../dto/role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private apiUrlGetRoles = `${environment.apiBaseUrl}/roles/all`;
  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) { }

  getAllRole(): Observable<Role[]> {
    return this.http.get<Role[]>(this.apiUrlGetRoles, this.apiConfig);
  }
}

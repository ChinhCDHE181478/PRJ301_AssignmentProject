import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { Employee } from '../dto/employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiUrlAll = `${environment.apiBaseUrl}/employees/all`;
  private apiUrlSearch = `${environment.apiBaseUrl}/employees/search`;

  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) { }

  all(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.apiUrlAll, this.apiConfig);
  }

  search(employee: Employee): Observable<Employee[]> {

    let params = new HttpParams();

    if(employee.employeeId){
      params = params.set("empId", employee.employeeId);
    }

    if(employee.employeeName) {
      params = params.set("name", employee.employeeName);
    }

    if(employee.department?.departmentId) {
      params = params.set("dId", employee.department.departmentId);
    }

    return this.http.get<Employee[]>(this.apiUrlSearch, {params, ...this.apiConfig});
  }
}

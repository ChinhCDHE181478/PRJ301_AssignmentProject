import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { UserLoginRequest } from '../dto/userLoginRequest';
import { Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { AccountRequest } from '../dto/accountRequest';
import { AccountResponse } from '../dto/accountResponse';
import { MessageResponse } from '../dto/messageResponse';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  
  private apiUrlLogin = `${environment.apiBaseUrl}/accounts/login`;
  private apiUrlCreate = `${environment.apiBaseUrl}/accounts/create`;
  private apiUrlUpdate = `${environment.apiBaseUrl}/accounts/update`;
  private apiUrlAll = `${environment.apiBaseUrl}/accounts/all`;
  private apiUrlSearch = `${environment.apiBaseUrl}/accounts/search`;
  private apiUrlDelete = `${environment.apiBaseUrl}/accounts/delete`;

  private apiConfig = {
    headers : new HttpHeaders({'Content-Type': 'application/json'}),
  }

  constructor(private http: HttpClient) { }

  login(userLogin: UserLoginRequest): Observable<any> {
    return this.http.post<any>(this.apiUrlLogin, userLogin, this.apiConfig);
  }

  create(account: AccountRequest): Observable<any> {
    return this.http.post<any>(this.apiUrlCreate, account, this.apiConfig);
  }

  update(account: AccountRequest): Observable<any> {
    return this.http.put<any>(this.apiUrlUpdate, account, this.apiConfig);
  }

  all(): Observable<AccountResponse[]> {
    return this.http.get<AccountResponse[]>(this.apiUrlAll, this.apiConfig);
  }

  search(account: AccountRequest): Observable<AccountResponse[]> {
    let params = new HttpParams();

    if(account.username){
      params = params.set('username', account.username);
    }

    if(account.employeeId){
      params = params.set('empId', account.employeeId);
    }

    if(account.role?.id){
      params = params.set('roleId', account.role.id);
    }

    if(account.status){
      params = params.set('status', account.status)
    }

    return this.http.get<AccountResponse[]>(this.apiUrlSearch, { params, ...this.apiConfig });
  }

  delete(id: number): Observable<MessageResponse> {
    return this.http.delete<MessageResponse>(`${this.apiUrlDelete}/${id}`, this.apiConfig);
  }

}

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { environment } from 'src/app/environment/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) { }

  public login(email: string, password: string, extendedValidity: boolean): Observable<any> {
    console.log(extendedValidity);

    return this.http.post(
      environment.apiUrl + '/auth/api/login',
      {
        username: email,
        password: password,
        extendedValidity: extendedValidity
      },
      httpOptions
    );
  }

  public register(email: string, password: string): Observable<any> {
    return this.http.post(
      environment.apiUrl + '/auth/api/register',
      {
        username: email,
        password: password
      },
      httpOptions
    );
  }

  public is_activated(email: string): Observable<any> {
    return this.http.get(
      environment.apiUrl + '/auth/api/is-activated',
      { params: { username: email } },
    );
  }

  public current_user(): Observable<any> {
    return this.http.get(
      environment.apiUrl + '/auth/api/current-user',
      { responseType: 'text' }
    );
  }

  public change_password(oldPassword: string, newPassword: string): Observable<any> {
    return this.http.put(
      environment.apiUrl + '/auth/api/change-password',
      {
        oldPassword: oldPassword,
        newPassword: newPassword
      },
      httpOptions
    );
  }
}
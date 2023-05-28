import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../environment/environment";

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  
  constructor(private http: HttpClient) { }

  public getSubjects(): Observable<any> {
    return this.http.get(
        environment.apiUrl + '/feedback/api/university-classes/me',
    );
  }
}

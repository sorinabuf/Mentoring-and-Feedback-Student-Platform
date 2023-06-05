import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../environment/environment";
import {Feedback} from "../models/feedback.model";

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  
  constructor(private http: HttpClient) { }

  public getCategorySubjects(): Observable<any> {
    return this.http.get(
        environment.apiUrl + '/feedback/api/university-classes/me',
    );
  }

  public getSubject(id: number): Observable<any> {
    return this.http.get(
        environment.apiUrl + '/feedback/api/university-classes/' + id.toString(),
    );
  }

  public postFeedback(feedback: Feedback): Observable<any> {
    return this.http.post(
        environment.apiUrl + '/feedback/api/feedbacks', feedback
    );
  }

  public getTeachingAssistants(universityClassId: number): Observable<any> {
    const params = new HttpParams()
        .set('universityClassId', universityClassId);

    return this.http.get(
        environment.apiUrl + '/feedback/api/teaching-assistants', {params}
    );
  }

  public getDaysUntilNextYear(): Observable<any> {
    return this.http.get(
        environment.apiUrl + '/feedback/api/next-university-year/days',
    );
  }

  public countFeedbackLeft(): Observable<any> {
    return this.http.get(
        environment.apiUrl + '/feedback/api/feedbacks/count',
    );
  }

  public getFeedbackFaculty(): Observable<any> {
    return this.http.get(
        environment.apiUrl + '/feedback/api/faculties/me/university-classes',
    );
  }

  public getFeedbackSubjectDetails(id: number): Observable<any> {
    return this.http.get(
        environment.apiUrl + '/feedback/api/university-classes/' + id.toString() +'/feedback-details',
    );
  }

  public getCategory(id: number): Observable<any> {
    return this.http.get(
        environment.apiUrl + '/feedback/api/categories/' + id.toString()
    );
  }
}

import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../environment/environment';
import { Faculty } from '../models/faculty.model';

@Injectable({
  providedIn: 'root'
})
export class CoreService {
  constructor(private http: HttpClient) { }

  public account_completed(): Observable<any> {
    return this.http.get(
      environment.apiUrl + '/core/api/students/completed-user',
    );
  }

  public get_all_faculties(): Observable<Faculty[]> {
    return this.http.get<Faculty[]>(
      environment.apiUrl + '/core/api/faculties',
    );
  }

  public get_cohorts(faculty_id: number, year: string): Observable<string[]> {
    return this.http.get<string[]>(
      environment.apiUrl + '/core/api/university-years/series',
      {
        params: {
          "facultyId": faculty_id,
          "year": year
        }
      }
    );
  }

  public complete_account(name: string, surname: string, email: string, faculty_id: number, year: string, series: string, group_num: string, image: File): Observable<any> {
    const formData = new FormData();

    const body = {
      firstName: name,
      lastName: surname,
      groupNum: group_num,
      studentEmail: email,
      personalEmail: email,
      universityYear: {
        year: year,
        series: series,
        facultyId: faculty_id
      }
    }

    formData.append('bodyData', JSON.stringify(body));
    formData.append('file', image);

    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');

    return this.http.post(
      environment.apiUrl + '/core/api/students',
      formData,
      {
        headers: headers
      });
  }

  public get_current_student(): Observable<any> {
    return this.http.get(
      environment.apiUrl + '/core/api/students/current-user'
    );
  }
}

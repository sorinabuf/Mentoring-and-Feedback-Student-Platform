import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from '../environment/environment';
import { Faculty } from '../models/faculty.model';
import { UniversityYear } from '../models/university-year-info.model';
import { UniversityClass } from '../models/university-class-info.model';
import { Teacher } from '../models/teacher.model';

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

  public add_faculty(faculty: Faculty): Observable<any> {
    return this.http.post(
      environment.apiUrl + '/core/api/faculties',
      faculty
    );
  }

  public update_faculty(faculty: Faculty): Observable<any> {
    return this.http.put(
      environment.apiUrl + '/core/api/faculties',
      faculty
    );
  }

  public delete_faculty(id: number): Observable<any> {
    return this.http.delete(
      environment.apiUrl + '/core/api/faculties/' + id
    );
  }

  public get_all_university_years(): Observable<UniversityYear[]> {
    return this.http.get<UniversityYear[]>(
      environment.apiUrl + '/core/api/university-years',
    );
  }

  public add_university_year(universityYear: UniversityYear): Observable<any> {
    return this.http.post(
      environment.apiUrl + '/core/api/university-years',
      universityYear
    );
  }

  public update_university_year(universityYear: UniversityYear): Observable<any> {
    return this.http.put(
      environment.apiUrl + '/core/api/university-years',
      universityYear
    );
  }

  public delete_university_year(id: number): Observable<any> {
    return this.http.delete(
      environment.apiUrl + '/core/api/university-years/' + id
    );
  }

  public get_all_university_classes(): Observable<UniversityClass[]> {
    return this.http.get<UniversityClass[]>(
      environment.apiUrl + '/core/api/university-classes',
    );
  }

  public add_university_class(universityClass: UniversityClass): Observable<any> {
    return this.http.post(
      environment.apiUrl + '/core/api/university-classes',
      universityClass
    );
  }

  public update_university_class(universityClass: UniversityClass): Observable<any> {
    return this.http.put(
      environment.apiUrl + '/core/api/university-classes',
      universityClass
    );
  }

  public delete_university_class(id: number): Observable<any> {
    return this.http.delete(
      environment.apiUrl + '/core/api/university-classes/' + id
    );
  }

  public get_all_teachers(): Observable<Teacher[]> {
    return this.http.get<Teacher[]>(
      environment.apiUrl + '/core/api/teachers',
    );
  }

  public add_teacher(teacher: Teacher): Observable<any> {
    return this.http.post(
      environment.apiUrl + '/core/api/teachers',
      teacher
    );
  }

  public update_teacher(teacher: Teacher): Observable<any> {
    return this.http.put(
      environment.apiUrl + '/core/api/teachers',
      teacher
    );
  }

  public delete_teacher(id: number): Observable<any> {
    return this.http.delete(
      environment.apiUrl + '/core/api/teachers/' + id
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

  public complete_account(id: number, name: string, surname: string, email: string, faculty_id: number, year: string, series: string, group_num: string, image: File): Observable<any> {
    const formData = new FormData();

    const body = {
      id: id,
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

  public update_account(id: number, name: string, surname: string, email: string, faculty_id: number, year: string, series: string, group_num: string): Observable<any> {
    const body = {
      id: id,
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

    return this.http.put(
      environment.apiUrl + '/core/api/students', body);
  }

  public get_current_student(): Observable<any> {
    return this.http.get(
      environment.apiUrl + '/core/api/students/current-user'
    );
  }
}

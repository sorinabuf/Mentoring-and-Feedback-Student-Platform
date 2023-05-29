import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../environment/environment';
import { MentorInfo } from '../models/mentor-info.model';
import { SubjectDetail } from '../models/subject-detail.model';
import { Skill } from '../models/skill.model';

@Injectable({
  providedIn: 'root'
})
export class MentorshipService {

  constructor(private http: HttpClient) { }

  public get_current_mentor(): Observable<MentorInfo> {
    return this.http.get<MentorInfo>(
      environment.apiUrl + '/mentorship/api/mentors/current-user'
    );
  }

  public create_mentor(mentorInfo: MentorInfo): Observable<any> {
    return this.http.post<MentorInfo>(
      environment.apiUrl + '/mentorship/api/mentors/details',
      mentorInfo
    );
  }

  public update_mentor(mentorInfo: MentorInfo): Observable<any> {
    return this.http.put<MentorInfo>(
      environment.apiUrl + '/mentorship/api/mentors/details',
      mentorInfo
    );
  }

  public delete_mentor(mentorId: number): Observable<any> {
    return this.http.delete(
      environment.apiUrl + '/mentorship/api/mentors/' + mentorId
    );
  }

  public get_mentor_possible_subjects(): Observable<SubjectDetail[]> {
    return this.http.get<SubjectDetail[]>(
      environment.apiUrl + '/mentorship/api/university-classes/mentorship'
    );
  }

  public get_skills(): Observable<Skill[]> {
    return this.http.get<Skill[]>(
      environment.apiUrl + '/mentorship/api/skills'
    );
  }
}

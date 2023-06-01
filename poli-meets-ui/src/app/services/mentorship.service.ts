import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../environment/environment';
import { MentorInfo } from '../models/mentor-info.model';
import { SubjectDetail } from '../models/subject-detail.model';
import { Skill } from '../models/skill.model';
import { MeetingSlot } from '../models/meeting-slot.model';
import { Meeting } from '../models/meeting.model';
import { MentorSlot } from '../models/mentor-slot.model';

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

  public get_meeting_free_slots(): Observable<MeetingSlot[]> {
    return this.http.get<MeetingSlot[]>(
      environment.apiUrl + '/mentorship/api/meeting-slots/current-user/free'
    );
  }

  public delete_meeting_free_slot(id: number): Observable<any> {
    return this.http.delete(
      environment.apiUrl + '/mentorship/api/meeting-slots/' + id
    );
  }

  public add_meeting_free_slot(date: Date): Observable<any> {
    return this.http.post(
      environment.apiUrl + '/mentorship/api/meeting-slots/current-user',
      {
        date: date
      }
    );
  }

  public get_all_upcoming_meetings(): Observable<Meeting[]> {
    return this.http.get<Meeting[]>(
      environment.apiUrl + '/mentorship/api/meeting-requests/current-user/mentor'
    );
  }

  public get_student_upcoming_meetings(): Observable<Meeting[]> {
    return this.http.get<Meeting[]>(
      environment.apiUrl + '/mentorship/api/meeting-requests/current-user/student'
    );
  }

  public get_all_meetings(): Observable<MentorSlot[]> {
    return this.http.get<MentorSlot[]>(
      environment.apiUrl + '/mentorship/api/mentor-slots/current-user'
    );
  }

  public delete_upcoming_meeting(meeting: Meeting): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: meeting
    };

    return this.http.delete(
      environment.apiUrl + '/mentorship/api/meeting-requests',
      httpOptions
    );
  }
}

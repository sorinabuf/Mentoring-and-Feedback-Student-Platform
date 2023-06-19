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
import { UniversityClass } from '../models/university-class.model';
import { MeetingRequest } from '../models/meeting-request.model';

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

  public get_all_mentors(): Observable<MentorInfo[]> {
    return this.http.get<MentorInfo[]>(
      environment.apiUrl + '/mentorship/api/mentors/current-user/all'
    );
  }

  public create_mentor(mentorInfo: MentorInfo): Observable<any> {
    return this.http.post<MentorInfo>(
      environment.apiUrl + '/mentorship/api/mentors/current-user/details',
      mentorInfo
    );
  }

  public update_mentor(mentorInfo: MentorInfo): Observable<any> {
    return this.http.put<MentorInfo>(
      environment.apiUrl + '/mentorship/api/mentors/current-user/details',
      mentorInfo
    );
  }

  public delete_mentor(): Observable<any> {
    return this.http.delete(
      environment.apiUrl + '/mentorship/api/mentors/current-user'
    );
  }

  public get_mentor_possible_subjects(): Observable<SubjectDetail[]> {
    return this.http.get<SubjectDetail[]>(
      environment.apiUrl + '/mentorship/api/university-classes/current-user/mentorship'
    );
  }

  public get_mentor_filter_subjects(): Observable<UniversityClass[]> {
    return this.http.get<UniversityClass[]>(
      environment.apiUrl + '/mentorship/api/university-classes/current-user/mentors'
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

  public get_mentor_free_slots(id: number): Observable<MeetingSlot[]> {
    return this.http.get<MeetingSlot[]>(
      environment.apiUrl + '/mentorship/api/meeting-slots',
      {
        params: {
          "mentorId": id
        }
      }
    );
  }

  public delete_meeting_free_slot(id: number): Observable<any> {
    return this.http.delete(
      environment.apiUrl + '/mentorship/api/meeting-slots/current-user/' + id
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
      environment.apiUrl + '/mentorship/api/meeting-requests/current-user/mentor/approved'
    );
  }

  public get_all_pending_meetings(): Observable<Meeting[]> {
    return this.http.get<Meeting[]>(
      environment.apiUrl + '/mentorship/api/meeting-requests/current-user/mentor/pending'
    );
  }

  public get_student_upcoming_meetings(): Observable<Meeting[]> {
    return this.http.get<Meeting[]>(
      environment.apiUrl + '/mentorship/api/meeting-requests/current-user/student/approved'
    );
  }

  public get_student_pending_meetings(): Observable<Meeting[]> {
    return this.http.get<Meeting[]>(
      environment.apiUrl + '/mentorship/api/meeting-requests/current-user/student/pending'
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
      environment.apiUrl + '/mentorship/api/meeting-requests/approved',
      httpOptions
    );
  }

  public delete_pending_meeting(meeting: Meeting): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: meeting
    };

    return this.http.delete(
      environment.apiUrl + '/mentorship/api/meeting-requests/pending',
      httpOptions
    );
  }

  public update_pending_meeting(meeting: Meeting): Observable<any> {
    return this.http.put(
      environment.apiUrl + '/mentorship/api/meeting-requests/current-user/mentor/pending',
      meeting
    );
  }

  public add_meeting_request(meeting: MeetingRequest): Observable<any> {
    return this.http.post(
      environment.apiUrl + '/mentorship/api/meeting-requests/current-user',
      meeting
    );
  }
}

import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, catchError, of } from 'rxjs';
import { MentorshipService } from '../services/mentorship.service';
import { MentorInfo } from '../models/mentor-info.model';

@Injectable({
  providedIn: 'root'
})
export class MentorInfoResolver implements Resolve<MentorInfo | undefined> {
  constructor(private mentorService: MentorshipService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MentorInfo | undefined> {
    return this.mentorService.get_current_mentor().pipe(
      catchError(() => {
        console.error("Not a mentor")

        return of(undefined);
      })
    )
  }
}

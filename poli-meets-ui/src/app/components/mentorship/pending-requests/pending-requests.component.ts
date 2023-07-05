import { Component, ElementRef, HostListener, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MeetingType } from 'src/app/models/meeting-type.model';
import { Meeting } from 'src/app/models/meeting.model';
import { MentorshipService } from 'src/app/services/mentorship.service';
import { ConfirmationDialogComponent } from '../../dialog/confirmation-dialog/confirmation-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import * as AOS from 'aos';

@Component({
  selector: 'app-pending-requests',
  templateUrl: './pending-requests.component.html',
  styleUrls: ['./pending-requests.component.scss', '../mentorship-header.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class PendingRequestsComponent {
  @ViewChild('requests') requestsHeader!: ElementRef;
  scrollPosition: number = 0;

  pendingRequests: Meeting[];
  myPendingRequests: Meeting[];
  pendingRequestsForMe: Meeting[];
  isMentor: boolean;
  numMentors: number;
  isLoading: boolean;

  constructor(private mentorshipService: MentorshipService, private dialog: MatDialog, private snackBar: MatSnackBar, private activatedRoute: ActivatedRoute) {
    this.pendingRequests = [];
    this.myPendingRequests = [];
    this.pendingRequestsForMe = [];
    this.isMentor = false;
    this.numMentors = 0;
    this.isLoading = false;
  }

  ngOnInit(): void {
    AOS.init();

    this.activatedRoute.data.subscribe(({ mentorInfo }) => {
      if (mentorInfo == undefined) {
        this.isMentor = false;
        return;
      }

      this.isMentor = true;
    });

    this.initPendingMeetings();

    this.mentorshipService.get_mentor_filter_subjects().subscribe((response) => {
      let subjects = response;

      this.mentorshipService.get_all_mentors().subscribe((response) => {
        let mentors = response.filter(mentor => mentor.subjects.map(subject => subject.id).some(subject => new Set(subjects.map(subject => subject.id)).has(subject)));

        this.numMentors = mentors.length;
      });
    });
  }

  initPendingMeetings(): void {
    if (this.isMentor) {
      this.mentorshipService.get_all_pending_meetings().subscribe((response) => {
        this.pendingRequests = response;

        this.myPendingRequests = this.pendingRequests.filter(meeting => meeting.type == MeetingType.MENTOR);

        this.pendingRequestsForMe = this.pendingRequests.filter(meeting => meeting.type == MeetingType.MENTEE);
      });
    } else {
      this.mentorshipService.get_student_pending_meetings().subscribe((response) => {
        this.myPendingRequests = response;
      })
    }
  }

  getNumPendingRequests(): number {
    return this.myPendingRequests.length + this.pendingRequestsForMe.length;
  }

  openDeleteDialog(meeting: Meeting): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.data = {
      title: 'Delete Meeting Request',
      message: 'Are you sure you want to delete your request?'
    };

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((response) => {
      if (response == "yes") {
        this.mentorshipService.delete_pending_meeting(meeting).subscribe(_ => {
          console.log('Successful request deletion');

          this.snackBar.open('Meeting request deleted', undefined, {
            duration: 3000
          });

          this.initPendingMeetings();
        });
      }
    });
  }


  openApproveDialog(meeting: Meeting): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.data = {
      title: 'Approve Meeting Request',
      message: 'Are you sure you want to approve this request?'
    };

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((response) => {
      if (response == "yes") {
        this.mentorshipService.update_pending_meeting(meeting).subscribe(_ => {
          console.log('Successful request approval');

          this.snackBar.open('Meeting request approved', undefined, {
            duration: 3000
          });

          this.initPendingMeetings();
        });
      }
    });
  }

  openDeclineDialog(meeting: Meeting): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.data = {
      title: 'Decline Meeting Request',
      message: 'Are you sure you want to decline this request?'
    };

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((response) => {
      if (response == "yes") {
        this.mentorshipService.delete_pending_meeting(meeting).subscribe(_ => {
          console.log('Successful request decline');

          this.snackBar.open('Meeting request declined', undefined, {
            duration: 3000
          });

          this.initPendingMeetings();
        });
      }
    });
  }

  getImage(meeting: Meeting): string {
    if (meeting.type == MeetingType.MENTEE) {
      return "data:image/png;base64," + meeting.student.image;
    }

    return "data:image/png;base64," + meeting.mentor.image;
  }

  getName(meeting: Meeting): string {
    if (meeting.type == MeetingType.MENTEE) {
      return meeting.student.firstName + ' ' + meeting.student.lastName;
    }

    return meeting.mentor.firstName + ' ' + meeting.mentor.lastName;
  }

  getEmail(meeting: Meeting): string {
    if (meeting.type == MeetingType.MENTEE) {
      return meeting.student.personalEmail;
    }

    return meeting.mentor.personalEmail;
  }

  getDate(meeting: Meeting): string {
    return new Date(meeting.meetingSlot.date).getDate() + ' '
      + this.monthNames[new Date(meeting.meetingSlot.date).getMonth()] + ' '
      + new Date(meeting.meetingSlot.date).getFullYear();
  }

  getTime(meeting: Meeting): string {
    let minutes = new Date(meeting.meetingSlot.date).getMinutes();
    let time = new Date(meeting.meetingSlot.date).getHours() + ':';

    if (minutes) {
      if (minutes < 10) {
        return time + '0' + minutes;
      } else {
        return time + minutes;
      }
    }

    return time + '00';
  }

  getSubject(meeting: Meeting): string {
    return meeting.mentorSubject.universityClass.name;
  }

  getDescription(meeting: Meeting): string {
    return meeting.description;
  }

  scrollToRequestsSection(): void {
    const timelineSectionHeight = this.requestsHeader.nativeElement.clientHeight;
    const offsetTop = this.requestsHeader.nativeElement.offsetTop;
    const scrollPosition = offsetTop - timelineSectionHeight + 64;

    window.scrollTo({
      top: scrollPosition,
      behavior: "smooth"
    });
  }


  displayScrollButton(): boolean {
    if (this.requestsHeader && this.scrollPosition > this.requestsHeader.nativeElement.offsetTop) {
      return true;
    }

    return false;
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(_: Event) {
    this.scrollPosition = window.pageYOffset;
  }

  monthNames: string[] = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
  ];
}

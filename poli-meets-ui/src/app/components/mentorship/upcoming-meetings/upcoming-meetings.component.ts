import { Component, ElementRef, ViewChild, ViewEncapsulation } from '@angular/core';
import { MentorshipService } from 'src/app/services/mentorship.service';

import * as AOS from 'aos';
import { MeetingSlot } from 'src/app/models/meeting-slot.model';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../dialog/confirmation-dialog/confirmation-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FreeSlotComponent } from '../../dialog/free-slot/free-slot.component';
import { Meeting } from 'src/app/models/meeting.model';
import { MeetingType } from 'src/app/models/meeting-type.model';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { MentorSlot } from 'src/app/models/mentor-slot.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-upcoming-meetings',
  templateUrl: './upcoming-meetings.component.html',
  styleUrls: ['./upcoming-meetings.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: [
    trigger('slideInOut', [
      state('in', style({
        height: '*',
        opacity: 1
      })),
      state('out', style({
        height: '0',
        opacity: 0
      })),
      transition('in <=> out', animate('250ms cubic-bezier(0.4, 0, 0.2, 1)')),
    ])
  ]
})
export class UpcomingMeetingsComponent {
  @ViewChild('timeline') timeline!: ElementRef;
  
  isMeetingPanelOpen: { [key: number]: boolean };
  freeSlots: MeetingSlot[];
  upcomingMeetings: Meeting[];
  filteredMeetings: Meeting[];
  upcomingMeetingsFirstColumn: Meeting[];
  upcomingMeetingsSecondColumn: Meeting[];
  allMeetings: any[];
  isMentor: boolean;

  filterChips = [
    {
      type: MeetingType.MENTOR,
      value: "Meetings With Mentors",
    },
    {
      type: MeetingType.MENTEE,
      value: "Meetings With Students",
    }];

  constructor(private mentorshipService: MentorshipService, private dialog: MatDialog, private snackBar: MatSnackBar, private activatedRoute: ActivatedRoute) {
    this.isMeetingPanelOpen = {};
    this.freeSlots = [];
    this.upcomingMeetings = [];
    this.filteredMeetings = [];
    this.upcomingMeetingsFirstColumn = [];
    this.upcomingMeetingsSecondColumn = [];
    this.allMeetings = [];
    this.isMentor = false;
  }

  ngOnInit() {
    AOS.init();

    this.activatedRoute.data.subscribe(({ mentorInfo }) => {
      if (mentorInfo == undefined) {
        this.isMentor = false;
        return;
      }

      this.isMentor = true;
    });

    this.updateFreeSlots();
    this.updateUpcomingMeetings();
    this.updateAllMeetings();
  }

  updateAllMeetings() {
    this.mentorshipService.get_all_meetings().subscribe((response) => {
      this.allMeetings = response;
    });
  }

  updateFreeSlots(): void {
    this.mentorshipService.get_meeting_free_slots().subscribe(
      (response) => {
        this.freeSlots = response;
      });
  }

  updateUpcomingMeetings(): void {
    if (this.isMentor) {
      this.mentorshipService.get_all_upcoming_meetings().subscribe(
        (response) => {
          this.upcomingMeetings = response;
          this.filteredMeetings = response;

          this.updateColumnsUpcomingMeetings();
        });
    } else {
      this.mentorshipService.get_student_upcoming_meetings().subscribe(
        (response) => {
          this.upcomingMeetings = response;
          this.filteredMeetings = response;

          this.updateColumnsUpcomingMeetings();
        });
    }
  }

  updateColumnsUpcomingMeetings(): void {
    let counter = 0;
    this.upcomingMeetingsFirstColumn = [];
    this.upcomingMeetingsSecondColumn = [];

    for (var meeting of this.filteredMeetings) {
      this.isMeetingPanelOpen[meeting.id] = false;

      if (counter % 2 == 0) {
        this.upcomingMeetingsFirstColumn.push(meeting);
      } else {
        this.upcomingMeetingsSecondColumn.push(meeting);
      }

      counter++;
    }
  }

  filterUpcomingMeetings(filter: any): void {
    let selectedValue = undefined;

    if (filter.selected) {
      selectedValue = filter.source.value;
    }

    if (!selectedValue) {
      this.filteredMeetings = this.upcomingMeetings;
      this.updateColumnsUpcomingMeetings();
      return;
    }

    if (selectedValue == MeetingType.MENTEE) {
      this.filteredMeetings = this.upcomingMeetings.filter(meeting => meeting.type == MeetingType.MENTEE);
      this.updateColumnsUpcomingMeetings();
      return;
    }

    if (selectedValue == MeetingType.MENTOR) {
      this.filteredMeetings = this.upcomingMeetings.filter(meeting => meeting.type == MeetingType.MENTOR);
      this.updateColumnsUpcomingMeetings();
    }
  }

  toggleMeetingPanel(meeting: Meeting): void {
    this.isMeetingPanelOpen[meeting.id] = !this.isMeetingPanelOpen[meeting.id];
  }

  openDeleteFreeSlotDialog(slot: MeetingSlot): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;

    dialogConfig.data = {
      title: 'Delete Booking Slot',
      message: 'Are you sure you want to delete this slot?'
    };

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      if (result === "yes") {

        this.mentorshipService.delete_meeting_free_slot(slot.id).subscribe(
          _ => {
            console.log("Free slot deletion successful");

            this.updateFreeSlots();
            this.updateAllMeetings();

            this.snackBar.open('Successful booking slot deletion', undefined, {
              duration: 3000
            });
          });
      }
    });
  }

  openDeleteUpcomingMeetingDialog(meeting: Meeting): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;

    dialogConfig.data = {
      title: 'Cancel Upcoming Meeting',
      message: 'Are you sure you want to cancel this meeting?',
      submessage: '<strong>IMPORTANT</strong>: Choosing <strong>Yes</strong> will automatically announce all the participants of the meeting status update and reopen this slot for booking.'
    };

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      if (result === "yes") {

        this.mentorshipService.delete_upcoming_meeting(meeting).subscribe(_ => {
          console.log("Upcoming meeting deletion successful");

          this.updateUpcomingMeetings();

          if (this.isMentor) {
            this.updateFreeSlots();
            this.updateAllMeetings();
          }

          this.snackBar.open('Successful meeting cancel', undefined, {
            duration: 3000
          });
        });
      }
    });
  }

  openAddFreeSlotDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.panelClass = "free-slot-dialog";

    const dialogRef = this.dialog.open(FreeSlotComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      if (result === "yes") {
        console.log("Free slot addition successful");

        this.updateFreeSlots();
        this.updateAllMeetings();

        this.snackBar.open('Successful booking slot add', undefined, {
          duration: 3000
        });
      }
    });
  }

  getMeetingType(meeting: any): string {
    if (meeting.type === MeetingType.MENTOR) {
      return "Meeting With Mentor";
    }

    if (meeting.type === MeetingType.MENTEE) {
      return "Meeting With Student";
    }

    return "Booking Slot"
  }

  getMeetingDay(meetingSlot: MeetingSlot): number {
    return new Date(meetingSlot.date).getDate();
  }

  getMeetingMonth(meetingSlot: MeetingSlot): string {
    return this.monthNames[new Date(meetingSlot.date).getMonth()].slice(0, 3).toUpperCase();
  }

  getMeetingHour(meetingSlot: MeetingSlot): number {
    return new Date(meetingSlot.date).getHours();
  }

  getMeetingMinute(meetingSlot: MeetingSlot): number {
    return new Date(meetingSlot.date).getMinutes();
  }

  getMeetingSubject(meeting: Meeting): string {
    return meeting.mentorSubject.universityClass.name;
  }

  getMeetingParticipantName(meeting: Meeting) {
    if (meeting.type === MeetingType.MENTOR) {
      return meeting.mentor.firstName + ' ' + meeting.mentor.lastName;
    }

    return meeting.student.firstName + ' ' + meeting.student.lastName;
  }

  getMeetingParticipantEmail(meeting: Meeting): string {
    if (meeting.type === MeetingType.MENTOR) {
      return meeting.mentor.personalEmail;
    }

    return meeting.student.personalEmail;
  }

  getDescriptionOwner(meeting: Meeting): string {
    if (meeting.type === MeetingType.MENTOR) {
      return "You";
    }

    return "Other";
  }

  getMeetingDate(meeting: MeetingSlot): string {
    return new Date(meeting.date).getDate() + ' '
      + this.monthNames[new Date(meeting.date).getMonth()] + ' '
      + new Date(meeting.date).getFullYear();
  }

  getMeetingTime(meeting: MentorSlot): string {
    let time = new Date(meeting.date).getHours() + ':'
      + new Date(meeting.date).getMinutes();

    if (new Date(meeting.date).getMinutes()) {
      return time;
    }

    return time + '0';
  }

  getImage(meeting: MentorSlot): string {
    if (!meeting.image) {
      return "assets/images/auth/non-binary-avatar.png";
    }
    return "data:image/png;base64," + meeting.image;
  }

  scrollToTimelineSection(): void {
    const timelineSectionHeight = this.timeline.nativeElement.clientHeight;
    const offsetTop = this.timeline.nativeElement.offsetTop;
    const scrollPosition = offsetTop - timelineSectionHeight + 64;

    window.scrollTo({
      top: scrollPosition,
      behavior: "smooth"
    });
  }

  displayScrollButton(): boolean {
    if (this.timeline && window.pageYOffset > this.timeline.nativeElement.offsetTop) {
      return true;
    }

    return false;
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

import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MeetingRequestStatus } from 'src/app/models/meeting-request-status.model';
import { MeetingRequest } from 'src/app/models/meeting-request.model';
import { MeetingSlot } from 'src/app/models/meeting-slot.model';
import { Subject } from 'src/app/models/subject.model';
import { MentorshipService } from 'src/app/services/mentorship.service';

@Component({
  selector: 'app-book-meeting-dialog',
  templateUrl: './book-meeting-dialog.component.html',
  styleUrls: ['./book-meeting-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class BookMeetingDialogComponent {
  bookMeetingForm: FormGroup;
  descriptionLength: number;
  subjects: Subject[];
  meetings: MeetingSlot[];
  isLoading: boolean;

  maxDescriptionLength = 200;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: { subjects: Subject[], meetings: MeetingSlot[] }, private mentorshipService: MentorshipService, private dialogRef: MatDialogRef<BookMeetingDialogComponent>,) {
    this.bookMeetingForm = this.fb.group({
      description: ['', {
        validators: [
          Validators.required,
        ],
      },
      ],
      subject: ['', {
        validators: [
          Validators.required,
        ],
      },
      ],
      meetingSlot: ['', {
        validators: [
          Validators.required,
        ],
      },
      ]
    });

    this.descriptionLength = 0;
    this.subjects = data.subjects;
    this.meetings = data.meetings;
    this.isLoading = false;
  }

  ngOnInit(): void {
    this.bookMeetingForm.controls['description'].valueChanges.subscribe(newDescription => {
      if (newDescription.length > this.maxDescriptionLength) {
        this.bookMeetingForm.controls['description'].setValue(newDescription.slice(0, this.maxDescriptionLength));
      } else {
        this.descriptionLength = newDescription.length;
      }
    });
  }

  getMeetingDate(meeting: MeetingSlot): string {
    return new Date(meeting.date).getDate() + ' '
      + this.monthNames[new Date(meeting.date).getMonth()] + ' '
      + new Date(meeting.date).getFullYear();
  }

  getMeetingTime(meeting: MeetingSlot): string {
    let time = new Date(meeting.date).getHours() + ':'
      + new Date(meeting.date).getMinutes();

    if (new Date(meeting.date).getMinutes()) {
      return time;
    }

    return time + '0';
  }

  onBookMeetingSubmit() : void {
    let meeting = new MeetingRequest(0, this.bookMeetingForm.controls['description'].value, MeetingRequestStatus.PENDING, this.bookMeetingForm.controls['meetingSlot'].value, 0, this.bookMeetingForm.controls['subject'].value);

    this.isLoading = true;

    this.mentorshipService.add_meeting_request(meeting).subscribe((response) =>{
      this.dialogRef.close(response);
      this.isLoading = false;
    })
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

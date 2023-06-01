import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MentorshipService } from 'src/app/services/mentorship.service';

@Component({
  selector: 'app-free-slot',
  templateUrl: './free-slot.component.html',
  styleUrls: ['./free-slot.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FreeSlotComponent {
  datetime: FormControl;

  constructor(private mentorshipService: MentorshipService, private dialogRef: MatDialogRef<FreeSlotComponent>) {
    this.datetime = new FormControl('');
  }

  ngOnInit() {
    this.datetime.updateValueAndValidity();
  }

  getCurrentDate(): Date {
    return new Date();
  }

  onFreeSlotSubmit(): void {
    console.log(new Date(this.datetime.value));

    this.mentorshipService.add_meeting_free_slot(new Date(this.datetime.value)).subscribe(_ => {
      this.dialogRef.close("yes");
    });
  }
}

import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CoreService } from 'src/app/services/core.service';

@Component({
  selector: 'app-photo-dialog',
  templateUrl: './photo-dialog.component.html',
  styleUrls: ['./photo-dialog.component.scss']
})
export class PhotoDialogComponent {
  student: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: { student: any }, private coreService: CoreService, private dialogRef: MatDialogRef<PhotoDialogComponent>,) {
    this.student = data.student;
  }

  onAccountInfoSubmit(profilePhoto: File): void {
    this.coreService.complete_account(
      this.student.id,
      this.student.firstName,
      this.student.lastName,
      this.student.studentEmail,
      this.student.universityYear.faculty.id,
      this.student.universityYear.year,
      this.student.universityYear.series,
      this.student.groupNum,
      profilePhoto).subscribe(
        (response) => {
          this.dialogRef.close(response);
        });
  }
}

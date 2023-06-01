import { Component, Input, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AccountInformationDialogComponent } from '../dialog/account-information-dialog/account-information-dialog.component';

import * as AOS from 'aos';
import { ConfirmationDialogComponent } from '../dialog/confirmation-dialog/confirmation-dialog.component';
import { MentorDialogComponent } from '../dialog/mentor-dialog/mentor-dialog.component';
import { ActivatedRoute } from '@angular/router';
import { Year } from 'src/app/models/year.model';
import { MentorshipService } from 'src/app/services/mentorship.service';
import { Skill } from 'src/app/models/skill.model';
import { Subject } from 'src/app/models/subject-simple.model';
import { PasswordValidator } from 'src/app/helpers/validators';
import { AuthService } from 'src/app/services/auth.service';
import { EMPTY, catchError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PhotoDialogComponent } from '../dialog/photo-dialog/photo-dialog.component';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MyAccountComponent {
  student: any | undefined;
  name: string | undefined;
  surname: string | undefined;
  email: string | undefined;
  faculty: string | undefined;
  study_cicle: string | undefined;
  specialty: string | undefined;
  year: string | undefined;
  cohort: string | undefined;
  group: string | undefined;
  isMentor: boolean | undefined;
  mentorId: number | undefined;
  description: string | undefined;
  skills: Skill[] | undefined;
  subjects: Subject[] | undefined;
  profilePhotoUrl: string | undefined;
  passwordForm: FormGroup;
  passwordVisibility: boolean;
  repasswordVisibility: boolean;
  errorNewPassword: boolean;
  errorPasswordMatch: boolean;
  errorOldPassword: boolean;

  constructor(private fb: FormBuilder, private dialog: MatDialog, private activatedRoute: ActivatedRoute, private mentorshipService: MentorshipService, private authService: AuthService, private snackBar: MatSnackBar) {
    this.passwordForm = this.fb.group({
      password: ['', {
        validators: [
          Validators.required,
        ],
      }
      ],
      repassword: ['', {
        validators: [
          Validators.required
        ],
      }
      ]
    });

    this.passwordVisibility = false;
    this.repasswordVisibility = false;
    this.errorNewPassword = false;
    this.errorPasswordMatch = false;
    this.errorOldPassword = false;
  }

  initData(student: any): void {
    this.student = student;
    this.name = student.firstName;
    this.surname = student.lastName;
    this.email = student.studentEmail;
    this.faculty = student.universityYear.faculty.name;
    this.specialty = student.universityYear.faculty.domain;
    this.year = Year[student.universityYear.year as keyof typeof Year];
    this.cohort = student.universityYear.series;
    this.group = student.groupNum;
    this.profilePhotoUrl = "data:image/png;base64," + student.image;

    if (this.year.includes("MASTER")) {
      this.study_cicle = "Master";
    } else {
      this.study_cicle = "License";
    }
  }

  ngOnInit() {
    AOS.init();

    this.activatedRoute.data.subscribe(({ student }) => {
      this.initData(student);
    });

    this.activatedRoute.data.subscribe(({ mentorInfo }) => {
      if (mentorInfo == undefined) {
        this.isMentor = false;
        return;
      }

      this.isMentor = true;
      this.mentorId = mentorInfo.id;
      this.description = mentorInfo.description;
      this.skills = mentorInfo.skills;
      this.subjects = mentorInfo.subjects;
    });
  }

  openPhotoEditDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = false;
    dialogConfig.data = {
      student: this.student
    }

    const dialogRef = this.dialog.open(PhotoDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((response) => {
      if (response === "close") {
        return;
      }

      console.log("Updated profile photo");

      this.snackBar.open('Successful profile photo update', undefined, {
        duration: 3000
      });

      this.initData(response);
    });
  }

  openAccountEditDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.data = {
      isEdit: true,
      id: this.student.id,
      name: this.name,
      surname: this.surname,
      faculty: this.student.universityYear.faculty.id,
      year: this.year,
      cohort: this.cohort,
      group: this.group,
      profilePhotoUrl: this.profilePhotoUrl,
      email: this.email
    }

    const dialogRef = this.dialog.open(AccountInformationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((response) => {
      if (response === "close") {
        return;
      }

      console.log("Updated account information");

      this.snackBar.open('Successful profile update', undefined, {
        duration: 3000
      });

      this.initData(response);
    });
  }

  endMentorship(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;

    dialogConfig.data = {
      title: 'End Mentorship',
      message: 'Are you sure you want to stop being a mentor?',
      submessage: '<strong>IMPORTANT</strong>: Choosing <strong>Yes</strong> will automatically remove your profile from the <strong>Mentors</strong> section and cancel all meetings with mentees.'
    };

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      if (result === "yes") {
        this.isMentor = false;

        this.mentorshipService.delete_mentor(this.mentorId!).subscribe(() => {
        });

        //TODO: delete current ongoing meetings
      }
    });
  }

  startMentorship(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.data = {
      isMentor: this.isMentor,
      mentorId: this.mentorId,
      description: this.description,
      skills: this.skills,
      subjects: this.subjects
    }
    dialogConfig.panelClass = "mentor-dialog";

    const dialogRef = this.dialog.open(MentorDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      if (result != "close") {
        if (this.isMentor) {
          console.log("Updated mentor information");

          this.snackBar.open('Successful mentor profile update', undefined, {
            duration: 3000
          });
        } else {
          console.log("Added mentor information");

          this.snackBar.open('Successful mentor profile completion', undefined, {
            duration: 3000
          });
        }

        this.isMentor = true;
        this.mentorId = result.id;
        this.description = result.description;
        this.skills = result.skills;
        this.subjects = result.subjects;
      }
    });
  }

  onPasswordChange(): void {
    if (this.passwordForm.controls['repassword'].value == this.passwordForm.controls['password'].value) {
      this.errorPasswordMatch = true;
      return;
    }

    const atIndex = this.email!.indexOf('@');
    const username = this.email!.substring(0, atIndex);
    const newPasswordValidator = PasswordValidator(username);

    this.passwordForm.controls['repassword'].setValidators([Validators.required, newPasswordValidator]);

    this.passwordForm.controls['repassword'].updateValueAndValidity();

    if (!this.passwordForm.valid) {
      this.errorNewPassword = true;
    } else {
      this.authService.change_password(
        this.passwordForm.controls['password'].value,
        this.passwordForm.controls['repassword'].value)
        .pipe(
          catchError(() => {
            this.errorOldPassword = true;
            console.error("Old password does not match current password")

            return EMPTY;
          })
        )
        .subscribe(() => {
          console.log("Password change successful")

          this.snackBar.open('Successful password change', undefined, {
            duration: 3000
          });

          this.clearPasswordForm();
        });
    }
  }

  clearPasswordErrors(): void {
    this.errorPasswordMatch = false;
    this.errorOldPassword = false;
  }

  clearRepasswordErrors(): void {
    this.errorNewPassword = false;
    this.errorPasswordMatch = false;
    this.errorOldPassword = false;

    this.passwordForm.controls['repassword'].setValidators([Validators.required]);

    this.passwordForm.controls['repassword'].updateValueAndValidity();
  }

  clearPasswordForm(): void {
    this.passwordForm.reset();
    this.errorNewPassword = false;
    this.errorPasswordMatch = false;
    this.errorOldPassword = false;
  }

  toggleVisibilityPassword(): void {
    this.passwordVisibility = !this.passwordVisibility;
  }

  getPasswordType(): string {
    if (this.passwordVisibility) {
      return "text";
    }

    return "password";
  }

  toggleVisibilityRepassword(): void {
    this.repasswordVisibility = !this.repasswordVisibility;
  }

  getRepasswordType(): string {
    if (this.repasswordVisibility) {
      return "text";
    }

    return "password";
  }

  getRepasswordInfo(): string {
    const passwordInfo = "New password must be at least 6 characters long and contain at least: 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character from the list [_#$%&^!?]."

    return passwordInfo;
  }

  getPasswordErrorMessage(): string {
    if (this.passwordForm.controls['repassword'].errors?.['invalidPasswordLength']) {
      return 'Your new password must be at least 6 characters long';
    }

    if (this.passwordForm.controls['repassword'].errors?.['invalidPasswordUppercase']) {
      return 'Your new password must contain at least 1 uppercase';
    }

    if (this.passwordForm.controls['repassword'].errors?.['invalidPasswordLowercase']) {
      return 'Your new password must contain at least 1 lowercase';
    }

    if (this.passwordForm.controls['repassword'].errors?.['invalidPasswordDigit']) {
      return 'Your new password must contain at least 1 digit';
    }

    if (this.passwordForm.controls['repassword'].errors?.['invalidPasswordSpecialChar']) {
      return 'Your new password must contain at least 1 special character (_#$%&^!?)';
    }

    if (this.passwordForm.controls['repassword'].errors?.['invalidPasswordContains']) {
      return 'Your new password can not contain your email';
    }

    return '';
  }
}

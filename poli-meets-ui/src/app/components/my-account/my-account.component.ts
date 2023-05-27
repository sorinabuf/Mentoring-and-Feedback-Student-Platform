import { Component, Input, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AccountInformationDialogComponent } from '../dialog/account-information-dialog/account-information-dialog.component';

import * as AOS from 'aos';
import { ConfirmationDialogComponent } from '../dialog/confirmation-dialog/confirmation-dialog.component';
import { MentorDialogComponent } from '../dialog/mentor-dialog/mentor-dialog.component';
import { ActivatedRoute } from '@angular/router';
import { Year } from 'src/app/models/year.model';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MyAccountComponent {
  name: string | undefined;
  surname: string | undefined;
  email: string | undefined;
  faculty: string | undefined;
  study_cicle: string | undefined;
  specialty: string | undefined;
  year: string | undefined;
  cohort: string | undefined;
  group: string | undefined;
  isMentor: boolean;
  @Input() description: string;
  @Input() skills: string[];
  @Input() subjects: string[];

  profilePhotoUrl: string | undefined;
  passwordForm: FormGroup;

  constructor(private fb: FormBuilder, private dialog: MatDialog, private activatedRoute: ActivatedRoute) {
    this.isMentor = true;
    this.description = "I enjoy discovering new technologies and parties. I worked for Adobe a while and now I am a freelancer."
    this.skills = ["C/C++", "Java", "Mongo", "Docker", "Algorithms"];
    this.subjects = ["OOP"];

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
  }

  ngOnInit() {
    window.addEventListener('load', () => {
      AOS.init();
    });

    this.activatedRoute.data.subscribe(({ student }) => {
      console.log(student);

      this.name = student.firstName[0].toUpperCase() + student.firstName.substr(1).toLowerCase();
      this.surname = student.lastName[0].toUpperCase() + student.lastName.substr(1).toLowerCase();
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
    })

    // this.dialog.open(MentorDialogComponent);
  }

  openAccountEditDialog(): void {
    // const dialogConfig = new MatDialogConfig();

    // dialogConfig.disableClose = true;
    // dialogConfig.data = {
    //   name: this.name,
    //   surname: this.surname,
    //   faculty: this.faculty,
    //   year: this.year,
    //   cohort: this.cohort,
    //   group: this.group
    // }

    // const dialogRef = this.dialog.open(AccountInformationDialogComponent, dialogConfig);

    // dialogRef.afterClosed().subscribe(() => {
    //   console.log("Updated account information");
    // });
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
      }
    });
  }
}

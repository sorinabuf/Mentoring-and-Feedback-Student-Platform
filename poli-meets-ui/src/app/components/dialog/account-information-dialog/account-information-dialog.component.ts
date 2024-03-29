import { animate, style, transition, trigger } from '@angular/animations';
import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import * as AOS from 'aos';

import { Faculty } from '../../../models/faculty.model';
import { CoreService } from '../../../services/core.service';
import { Year } from '../../../models/year.model';
import { AuthService } from '../../../services/auth.service';
import { HttpClient } from '@angular/common/http';
import { createFile } from 'src/app/helpers/methods';

@Component({
  selector: 'app-account-information-dialog',
  templateUrl: './account-information-dialog.component.html',
  styleUrls: ['./account-information-dialog.component.scss'],
  animations: [
    trigger('fadeAnimation', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms linear', style({ opacity: 1 }))
      ])
    ])
  ],
  encapsulation: ViewEncapsulation.None
})
export class AccountInformationDialogComponent {
  accountSetupStep: number;
  accountInfoForm: FormGroup;
  nameInfoForm: FormGroup;
  facultyInfoForm: FormGroup;
  groupInfoForm: FormGroup;
  faculties: Faculty[];
  years: string[];
  cohorts: string[];
  isFormLoading: boolean;
  isEdit: boolean;
  initialData: any;
  fieldChanges: boolean[];
  isFormChanged: boolean;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: { isEdit: boolean, id: number, name: string, surname: string, faculty: number, year: string, cohort: string, group: string, profilePhotoUrl: string, email: string }, private coreService: CoreService,
    private authService: AuthService,
    private dialogRef: MatDialogRef<AccountInformationDialogComponent>, private http: HttpClient) {
    this.accountSetupStep = 0;
    this.isEdit = false;
    this.initialData = data;
    this.fieldChanges = [false, false, false, false, false, false];
    this.isFormChanged = false;

    if (data.isEdit) {
      this.accountSetupStep = 1;
      this.isEdit = true;
    }

    this.nameInfoForm = this.fb.group({
      name: [data.name, {
        validators: [
          Validators.required,
        ],
      }
      ],
      surname: [data.surname, {
        validators: [
          Validators.required,
        ],
      }
      ]
    });

    this.facultyInfoForm = this.fb.group({
      faculty: [data.faculty, {
        validators: [
          Validators.required,
        ],
      },
      ],
      year: [data.year, {
        validators: [
          Validators.required,
        ],
      },
      ]
    });

    this.groupInfoForm = this.fb.group({
      cohort: [data.cohort, {
        validators: [
          Validators.required,
        ],
      },
      ],
      group: [data.group, {
        validators: [
          Validators.required,
          Validators.pattern('^[0-9][0-9][0-9]$'),
        ],
      },
      ]
    });

    this.accountInfoForm = this.fb.group({
      nameInfo: this.nameInfoForm,
      facultyInfo: this.facultyInfoForm,
      groupInfo: this.groupInfoForm
    });

    this.faculties = [];
    this.years = Object.values(Year);
    this.cohorts = [];

    if (data.faculty && data.year) {
      this.getCohorts();
    }

    this.nameInfoForm.controls['name'].valueChanges.subscribe(newName => {
      if (newName != this.initialData.name) {
        this.fieldChanges[0] = true;
      } else {
        this.fieldChanges[0] = false;
      }

      this.isFormChanged = this.fieldChanges.includes(true);
    });

    this.nameInfoForm.controls['surname'].valueChanges.subscribe(newSurname => {
      if (newSurname != this.initialData.surname) {
        this.fieldChanges[1] = true;
      } else {
        this.fieldChanges[1] = false;
      }

      this.isFormChanged = this.fieldChanges.includes(true);
    });

    this.facultyInfoForm.controls['faculty'].valueChanges.subscribe(newFaculty => {
      if (this.facultyInfoForm.controls['year'].value) {
        this.getCohorts();
      }

      if (newFaculty != this.initialData.faculty) {
        this.fieldChanges[2] = true;
      } else {
        this.fieldChanges[2] = false;
      }

      this.isFormChanged = this.fieldChanges.includes(true);
    });

    this.facultyInfoForm.controls['year'].valueChanges.subscribe(newYear => {
      if (this.facultyInfoForm.controls['faculty'].value) {
        this.getCohorts();
      }

      if (newYear != this.initialData.year) {
        this.fieldChanges[3] = true;
      } else {
        this.fieldChanges[3] = false;
      }

      this.isFormChanged = this.fieldChanges.includes(true);
    });

    this.groupInfoForm.controls['cohort'].valueChanges.subscribe(newCohort => {
      if (newCohort != this.initialData.cohort) {
        this.fieldChanges[4] = true;
      } else {
        this.fieldChanges[4] = false;
      }

      this.isFormChanged = this.fieldChanges.includes(true);
    });

    this.groupInfoForm.controls['group'].valueChanges.subscribe(newGroup => {
      if (newGroup != this.initialData.group) {
        this.fieldChanges[5] = true;
      } else {
        this.fieldChanges[5] = false;
      }

      this.isFormChanged = this.fieldChanges.includes(true);
    });

    this.isFormLoading = false;
  }

  ngOnInit() {
    window.addEventListener('load', () => {
      AOS.init();
    });

    this.coreService.get_all_faculties().subscribe(
      (response) => {
        this.faculties = response;
      });
  }

  nextStep(): void {
    this.accountSetupStep++;
  }

  previousStep(): void {
    this.accountSetupStep--;
  }

  goToStep(step: number): void {
    this.accountSetupStep = step;
  }

  isStepAvailable(step: number): boolean {
    if (step <= this.accountSetupStep) {
      return true;
    }

    if (step > this.accountSetupStep) {
      if (step == 2 && this.nameInfoForm.valid) {
        return true;
      }

      if (step == 3) {
        if (this.accountSetupStep == 2 && this.facultyInfoForm.valid) {
          return true;
        }

        if (this.accountSetupStep == 1 && this.nameInfoForm.valid && this.facultyInfoForm.valid) {
          return true;
        }
      }

      if (step == 4) {
        if (this.accountSetupStep == 3 && this.groupInfoForm.valid) {
          return true;
        }

        if (this.accountSetupStep == 2 && this.facultyInfoForm.valid && this.groupInfoForm.valid) {
          return true;
        }

        if (this.accountSetupStep == 1 && this.nameInfoForm.valid && this.facultyInfoForm.valid && this.groupInfoForm.valid) {
          return true;
        }
      }
    }

    return false;
  }

  getCohorts(): void {
    const faculty_id = this.facultyInfoForm.controls["faculty"].value;

    const year = this.facultyInfoForm.controls["year"].value;
    const indexOfYear = Object.values(Year).indexOf(year);
    const yearEnum = Object.keys(Year)[indexOfYear];

    this.coreService.get_cohorts(faculty_id, yearEnum).subscribe(
      (response) => {
        this.cohorts = response;

        if (!this.cohorts.includes(this.groupInfoForm.controls["cohort"].value)) {
          this.groupInfoForm.controls["cohort"].reset();
        }
      });
  }

  onAccountInfoSubmit(profilePhoto: File): void {
    const year = this.facultyInfoForm.controls["year"].value;
    const indexOfYear = Object.values(Year).indexOf(year);
    const yearEnum = Object.keys(Year)[indexOfYear];

    this.isFormLoading = true;

    setTimeout(() => {
      this.authService.current_user().subscribe(
        (response) => {
          const email = response;

          this.coreService.complete_account(0,
            this.nameInfoForm.controls['name'].value,
            this.nameInfoForm.controls['surname'].value,
            email,
            this.facultyInfoForm.controls["faculty"].value,
            yearEnum,
            this.groupInfoForm.controls['cohort'].value,
            this.groupInfoForm.controls['group'].value,
            profilePhoto).subscribe(
              () => {
                this.dialogRef.close();
              });
        });
    }, 500); // fake traffic
  }

  onAccountInfoEdit(): void {
    const year = this.facultyInfoForm.controls["year"].value;
    const indexOfYear = Object.values(Year).indexOf(year);
    const yearEnum = Object.keys(Year)[indexOfYear];

    let fileUrl = this.data.profilePhotoUrl;

    createFile(fileUrl, 'image.png').then((response) => {
      let file = response;

      this.coreService.complete_account(this.data.id,
        this.nameInfoForm.controls['name'].value,
        this.nameInfoForm.controls['surname'].value,
        this.data.email,
        this.facultyInfoForm.controls["faculty"].value,
        yearEnum,
        this.groupInfoForm.controls['cohort'].value,
        this.groupInfoForm.controls['group'].value,
        file).subscribe(
          (response) => {
            this.dialogRef.close(response);
          });
    });
  }
}

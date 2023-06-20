import { Component, Inject, Input, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Faculty } from 'src/app/models/faculty.model';
import { UniversityYear } from 'src/app/models/university-year-info.model';
import { Year } from 'src/app/models/year.model';

@Component({
  selector: 'app-university-year-admin-dialog',
  templateUrl: './university-year-admin-dialog.component.html',
  styleUrls: ['./university-year-admin-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class UniversityYearAdminDialogComponent {
  isEdit: boolean;
  adminForm: FormGroup;
  universityYearId: number;
  initialUniversityYear: UniversityYear | undefined;
  faculties: Faculty[];
  years: string[];

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: { universityYear: UniversityYear | undefined, faculties: Faculty[] }) {
    let faculty = null;
    let year = '';
    let cohort = '';
    this.isEdit = false;
    this.universityYearId = 0;
    this.years = Object.values(Year);

    if (data.universityYear) {
      this.universityYearId = data.universityYear.id;
      this.isEdit = true;
      faculty = data.universityYear.faculty.id;
      year = Year[data.universityYear.year as keyof typeof Year];
      cohort = data.universityYear.series;

      this.initialUniversityYear = data.universityYear;
    }

    this.faculties = data.faculties;

    this.adminForm = this.fb.group({
      faculty: [faculty, {
        validators: [
          Validators.required,
        ],
      },
      ],
      year: [year, {
        validators: [
          Validators.required,
        ],
      },
      ],
      cohort: [cohort, {
        validators: [
          Validators.required,
        ],
      },
      ]
    });
  }

  getNewEntry(): UniversityYear {
    const year = this.adminForm.controls["year"].value;
    const indexOfYear = Object.values(Year).indexOf(year);
    const yearEnum = Object.keys(Year)[indexOfYear];
    const faculty = this.faculties.filter(faculty => faculty.id == this.adminForm.controls['faculty'].value)[0];

    let universityYear = new UniversityYear(
      this.universityYearId,
      yearEnum,
      this.adminForm.controls['cohort'].value,
      faculty
    );

    return universityYear;
  }

  isFormModified(): boolean {
    if (this.isFormValid()) {
      let newUniversityYear = this.getNewEntry();

      if (this.initialUniversityYear) {
        return !(this.initialUniversityYear.id == newUniversityYear.id &&
          this.initialUniversityYear.faculty.id == newUniversityYear.faculty.id &&
          this.initialUniversityYear.year == newUniversityYear.year &&
          this.initialUniversityYear.series == newUniversityYear.series);
      }

      return true;
    }

    return false;
  }

  isFormValid(): boolean {
    return this.adminForm.valid;
  }
}

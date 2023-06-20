import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Semester } from 'src/app/models/semester.model';
import { Teacher } from 'src/app/models/teacher.model';
import { UniversityClass } from 'src/app/models/university-class-info.model';
import { UniversityYear } from 'src/app/models/university-year-info.model';
import { Year } from 'src/app/models/year.model';

@Component({
  selector: 'app-university-class-admin-dialog',
  templateUrl: './university-class-admin-dialog.component.html',
  styleUrls: ['./university-class-admin-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class UniversityClassAdminDialogComponent {
  isEdit: boolean;
  adminForm: FormGroup;
  universityClassId: number;
  initialUniversityClass: UniversityClass | undefined;
  teachers: Teacher[];
  semesters: string[];
  universityYears: UniversityYear[];

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: { universityClass: UniversityClass | undefined, teachers: Teacher[], universityYears: UniversityYear[] }) {
    let name = '';
    let abbreviation = '';
    let description = '';
    let teacher = null;
    let universityYear = null;
    let semester = '';
    this.isEdit = false;
    this.universityClassId = 0;
    this.semesters = Object.values(Semester);

    if (data.universityClass) {
      this.universityClassId = data.universityClass.id;
      this.isEdit = true;
      name = data.universityClass.name;
      abbreviation = data.universityClass.abbreviation;
      description = data.universityClass.description;
      teacher = data.universityClass.teacher.id;
      universityYear = data.universityClass.universityYear.id;
      semester = Semester[data.universityClass.semester as string as keyof typeof Semester];

      this.initialUniversityClass = data.universityClass;
    }

    this.teachers = data.teachers;
    this.universityYears = data.universityYears;

    this.adminForm = this.fb.group({
      name: [name, {
        validators: [
          Validators.required,
        ],
      },
      ],
      abbreviation: [abbreviation, {
        validators: [
          Validators.required,
        ],
      },
      ],
      description: [description, {
        validators: [
          Validators.required,
        ],
      },
      ],
      teacher: [teacher, {
        validators: [
          Validators.required,
        ],
      },
      ],
      universityYear: [universityYear, {
        validators: [
          Validators.required,
        ],
      },
      ],
      semester: [semester, {
        validators: [
          Validators.required,
        ],
      },
      ]
    });
  }

  getYearValue(year: string): string {
    return Year[year as keyof typeof Year];
  }

  getNewEntry(): UniversityClass {
    const semester = Object.keys(Semester).find(semester => Semester[semester as keyof typeof Semester] === this.adminForm.controls['semester'].value) as Semester;

    const teacher = this.teachers.filter(teacher => teacher.id == this.adminForm.controls['teacher'].value)[0];

    const universityYear = this.universityYears.filter(universityYear => universityYear.id == this.adminForm.controls['universityYear'].value)[0];

    let universityClass = new UniversityClass(
      this.universityClassId,
      this.adminForm.controls['name'].value,
      this.adminForm.controls['abbreviation'].value,
      this.adminForm.controls['description'].value,
      semester,
      teacher,
      universityYear
    );

    return universityClass;
  }

  isFormModified(): boolean {
    let newUniversityClass = this.getNewEntry();

    if (this.initialUniversityClass) {
      return !(this.initialUniversityClass.id == newUniversityClass.id &&
        this.initialUniversityClass.name == newUniversityClass.name &&
        this.initialUniversityClass.abbreviation == newUniversityClass.abbreviation &&
        this.initialUniversityClass.description == newUniversityClass.description &&
        this.initialUniversityClass.teacher.id == newUniversityClass.teacher.id &&
        this.initialUniversityClass.universityYear.id == newUniversityClass.universityYear.id &&
        this.initialUniversityClass.semester == newUniversityClass.semester);
    }

    return true;
  }

  isFormValid(): boolean {
    return this.adminForm.valid;
  }
}

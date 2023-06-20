import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Teacher } from 'src/app/models/teacher.model';

@Component({
  selector: 'app-teacher-admin-dialog',
  templateUrl: './teacher-admin-dialog.component.html',
  styleUrls: ['./teacher-admin-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class TeacherAdminDialogComponent {
  isEdit: boolean;
  adminForm: FormGroup;
  teacherId: number;
  initialTeacher: Teacher | undefined;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: { teacher: Teacher | undefined }) {
    let firstName = '';
    let lastName = '';
    this.isEdit = false;
    this.teacherId = 0;

    if (data.teacher) {
      this.teacherId = data.teacher.id;
      this.isEdit = true;
      firstName = data.teacher.firstName;
      lastName = data.teacher.lastName;

      this.initialTeacher = data.teacher;
    }

    this.adminForm = this.fb.group({
      firstName: [firstName, {
        validators: [
          Validators.required,
        ],
      },
      ],
      lastName: [lastName, {
        validators: [
          Validators.required,
        ],
      },
      ],
    });
  }

  getNewEntry(): Teacher {
    let teacher = new Teacher(
      this.teacherId,
      this.adminForm.controls['firstName'].value,
      this.adminForm.controls['lastName'].value
    );

    return teacher;
  }

  isFormModified(): boolean {
    let newTeacher = this.getNewEntry();

    if (this.initialTeacher) {
      return !(this.initialTeacher.id == newTeacher.id &&
        this.initialTeacher.firstName == newTeacher.firstName &&
        this.initialTeacher.lastName == newTeacher.lastName);
    }

    return true;
  }

  isFormValid(): boolean {
    return this.adminForm.valid;
  }
}

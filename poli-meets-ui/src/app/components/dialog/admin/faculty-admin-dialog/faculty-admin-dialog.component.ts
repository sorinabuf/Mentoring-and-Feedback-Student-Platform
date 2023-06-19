import { Component, Inject, Input, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Faculty } from 'src/app/models/faculty.model';

@Component({
  selector: 'app-faculty-admin-dialog',
  templateUrl: './faculty-admin-dialog.component.html',
  styleUrls: ['./faculty-admin-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FacultyAdminDialogComponent {
  isEdit: boolean;
  adminForm: FormGroup;
  facultyId: number;
  initialFaculty: Faculty | undefined;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: { faculty: Faculty | undefined }) {
    let name = '';
    let domain = '';
    let description = '';
    this.isEdit = false;
    this.facultyId = 0;

    if (data.faculty) {
      this.facultyId = data.faculty.id;
      this.isEdit = true;
      name = data.faculty.name;
      domain = data.faculty.domain;
      description = data.faculty.description;

      this.initialFaculty = data.faculty;
    }

    this.adminForm = this.fb.group({
      name: [name, {
        validators: [
          Validators.required,
        ],
      },
      ],
      domain: [domain, {
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
      ]
    });
  }

  getNewEntry(): Faculty {
    let faculty = new Faculty(
      this.facultyId,
      this.adminForm.controls['name'].value,
      this.adminForm.controls['domain'].value,
      this.adminForm.controls['description'].value
    );

    return faculty;
  }

  isFormModified(): boolean {
    let newFaculty = this.getNewEntry();

    if (this.initialFaculty) {
      return !(this.initialFaculty.id == newFaculty.id &&
        this.initialFaculty.name == newFaculty.name &&
        this.initialFaculty.domain == newFaculty.domain &&
        this.initialFaculty.description == newFaculty.description);
    }

    return true;
  }

  isFormValid(): boolean {
    return this.adminForm.valid;
  }
}

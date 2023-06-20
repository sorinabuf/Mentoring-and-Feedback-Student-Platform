import {Component, Inject, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Category} from "../../../../models/category.model";

@Component({
  selector: 'app-category-admin-dialog',
  templateUrl: './category-admin-dialog.component.html',
  styleUrls: ['./category-admin-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CategoryAdminDialogComponent {
  isEdit: boolean;
  adminForm: FormGroup;
  categoryId: number | undefined;
  initialCategory: Category | undefined;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: { category: Category | undefined }) {
    let name: string | undefined = '';
    let gradeQuestion: string | undefined = '';
    let textQuestion: string | undefined = '';
    this.isEdit = false;
    this.categoryId = 0;

    if (data.category) {
      this.categoryId = data.category.id;
      this.isEdit = true;
      name = data.category.name;
      gradeQuestion = data.category.gradeQuestion;
      textQuestion = data.category.feedbackTextQuestion;

      this.initialCategory= data.category;
    }

    this.adminForm = this.fb.group({
      name: [name, {
        validators: [
          Validators.required,
        ],
      },
      ],
      gradeQuestion: [gradeQuestion, {
        validators: [
          Validators.required,
        ],
      },
      ],
      textQuestion: [textQuestion, {
        validators: [
          Validators.required,
        ],
      },
      ]
    });
  }

  getNewEntry(): Category {
    return new Category(
        this.categoryId,
        this.adminForm.controls['name'].value,
        this.adminForm.controls['gradeQuestion'].value,
        this.adminForm.controls['textQuestion'].value
    );
  }

  isFormModified(): boolean {
    let newCategory = this.getNewEntry();

    if (this.initialCategory) {
      return !(this.initialCategory.id == newCategory.id &&
        this.initialCategory.name == newCategory.name &&
        this.initialCategory.gradeQuestion == newCategory.gradeQuestion &&
        this.initialCategory.feedbackTextQuestion == newCategory.feedbackTextQuestion);
    }

    return true;
  }

  isFormValid(): boolean {
    return this.adminForm.valid;
  }
}

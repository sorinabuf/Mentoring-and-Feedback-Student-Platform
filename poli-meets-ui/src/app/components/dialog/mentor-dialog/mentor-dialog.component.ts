import { Component, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-mentor-dialog',
  templateUrl: './mentor-dialog.component.html',
  styleUrls: ['./mentor-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MentorDialogComponent {
  mentorInfoForm: FormGroup;
  skills: string[];
  subjects: string[];
  value: string  = "";

  filterSubjects(event : any) {
    console.log(event);
  }

  constructor(private fb: FormBuilder) {
    this.mentorInfoForm = this.fb.group({
      skills: ['', {
        validators: [
          Validators.required,
        ],
      },
      ],
      subjects: ['', {
        validators: [
          Validators.required,
        ],
      },
      ]
    });

    this.skills = ["C/C++", "Java", "Mongo", "Docker", "Algorithms", "SQL"];
    this.subjects = ["OOP", "PC"];
  }
}

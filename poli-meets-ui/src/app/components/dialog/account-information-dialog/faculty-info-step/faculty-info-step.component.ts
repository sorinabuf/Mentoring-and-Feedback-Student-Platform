import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Faculty } from 'src/app/models/faculty.model';

@Component({
  selector: 'app-faculty-info-step',
  templateUrl: './faculty-info-step.component.html',
  styleUrls: ['./faculty-info-step.component.scss', '../account-form-style.scss']
})
export class FacultyInfoStepComponent {
  @Input() facultyInfoForm!: FormGroup;
  @Input() faculties : Faculty[] = [];
  @Input() years : string[] = [];

  @Output() prev = new EventEmitter<void>();
  @Output() next = new EventEmitter<void>();
}

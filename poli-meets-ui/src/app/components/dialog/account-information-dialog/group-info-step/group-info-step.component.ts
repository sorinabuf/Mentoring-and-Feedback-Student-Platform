import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-group-info-step',
  templateUrl: './group-info-step.component.html',
  styleUrls: ['./group-info-step.component.scss']
})
export class GroupInfoStepComponent {
  @Input() isLoading: boolean;
  @Input()isFormChanged: boolean;
  @Input() groupInfoForm!: FormGroup;
  @Input() cohorts: string[] = [];
  @Input() isEdit!: boolean;

  constructor() {
    this.isLoading = false;
    this.isFormChanged = false;
  }

  @Output() prev = new EventEmitter<void>();
  @Output() next = new EventEmitter<void>();
}

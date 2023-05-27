import { Component, EventEmitter, Input, Output, ViewEncapsulation } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-name-info-step',
  templateUrl: './name-info-step.component.html',
  styleUrls: ['./name-info-step.component.scss', '../account-form-style.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NameInfoStepComponent {
  @Input() nameInfoForm!: FormGroup;
  @Output() next = new EventEmitter<void>();
}

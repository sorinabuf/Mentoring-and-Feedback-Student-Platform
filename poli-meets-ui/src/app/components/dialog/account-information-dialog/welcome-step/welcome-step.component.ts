import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-welcome-step',
  templateUrl: './welcome-step.component.html',
  styleUrls: ['./welcome-step.component.scss']
})
export class WelcomeStepComponent {
  @Output() next = new EventEmitter<void>();
}

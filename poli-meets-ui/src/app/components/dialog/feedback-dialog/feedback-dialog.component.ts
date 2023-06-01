import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-feedback-dialog',
  templateUrl: './feedback-dialog.component.html',
  styleUrls: ['./feedback-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})

export class FeedbackDialogComponent {
  dialogTitle: string;
  dialogMessage: string;
  dialogSubMessage: string;

  constructor(@Inject(MAT_DIALOG_DATA) data: any) {
    this.dialogTitle = data.title;
    this.dialogMessage = data.message;
    this.dialogSubMessage = data.submessage;
  }
}

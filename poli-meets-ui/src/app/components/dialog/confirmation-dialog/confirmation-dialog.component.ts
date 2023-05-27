import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ConfirmationDialogComponent {
  dialogTitle: string;
  dialogMessage: string;
  dialogSubMessage: string;

  constructor(@Inject(MAT_DIALOG_DATA) data: any) {
    this.dialogTitle = data.title;
    this.dialogMessage = data.message;
    this.dialogSubMessage = data.submessage;
  }
}

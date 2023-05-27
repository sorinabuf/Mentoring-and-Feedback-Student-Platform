import { Component, EventEmitter, Input, Output } from '@angular/core';
import { createFile } from 'src/app/helpers/methods';

@Component({
  selector: 'app-photo-step',
  templateUrl: './photo-step.component.html',
  styleUrls: ['./photo-step.component.scss', '../account-form-style.scss']
})
export class PhotoStepComponent {
  @Input() isLoading : boolean;
  @Output() prev = new EventEmitter<void>();
  @Output() submit = new EventEmitter<File>();

  selectedAvatar: string;
  fileReader: FileReader;
  selectedPhoto: string;
  selectedPhotoName: string;
  selectedFile: File | undefined;

  constructor() {
    this.selectedAvatar = "";
    this.fileReader = new FileReader();
    this.selectedPhoto = "";
    this.selectedPhotoName = "No file selected";
    this.isLoading = false;
  }

  selectAvatar(avatar: string): void {
    if (this.selectedAvatar == avatar) {
      this.selectedAvatar = "";
    } else {
      this.selectedAvatar = avatar;
      this.discardPhoto();
    }
  }

  selectPhoto(event: any): void {
    if (!event.target.files.length) {
      return;
    }

    this.selectedFile = event.target.files[0];
    this.selectedPhotoName = event.target.files[0].name;
    this.selectedAvatar = "";

    this.fileReader.readAsDataURL(event.target.files[0]);
    this.fileReader.onload = () => {
      this.selectedPhoto = this.fileReader.result as string;
    };
  }

  discardPhoto(): void {
    this.selectedPhoto = "";
    this.selectedPhotoName = "No file selected";
  }

  async onAccountInfoSubmit(): Promise<void> {
    if (this.selectedPhoto) {
      this.submit.emit(this.selectedFile);
    } else {
      this.submit.emit(await createFile(`http://localhost:4200/assets/images/auth/${this.selectedAvatar}-avatar.png`, `${this.selectedAvatar}.png`));
    }
  }
}

import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

import { ConfirmationDialogComponent } from '../../dialog/confirmation-dialog/confirmation-dialog.component';
import { localStorageKey } from '../../../helpers/constants';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  @Input() transparent: boolean;

  mentorshipMenuItems: string[] = ["Mentors", "Pending Requests", "Upcoming Meetings", "Edit Account"];

  feedbackMenuItems: string[] = ["Give feedback", "All feedback"];

  constructor(private dialog: MatDialog, private router: Router) {
    this.transparent = false;
  }

  hasTransparentBackground(): boolean {
    if (!this.transparent) {
      return false;
    }

    if (window.scrollY > window.innerHeight / 2) {
      return false;
    }

    return true;
  }

  getLogoutButtonColor(): string {
    if (this.hasTransparentBackground()) {
      return 'primary';
    }

    return 'accent';
  }

  openLogoutDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;

    dialogConfig.data = {
      title: 'Logout',
      message: 'Are you sure you want to logout?'
    };

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      if (result === "yes") {
        console.log("Logout successful");

        localStorage.removeItem(localStorageKey);
        this.router.navigate(["/login"]);
      }
    });
  }

  goToMyAccount(): void {
    this.router.navigate(["/my-account"]);
  }
}

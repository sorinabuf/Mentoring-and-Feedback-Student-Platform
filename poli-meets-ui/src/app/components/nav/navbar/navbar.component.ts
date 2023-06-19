import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

import { ConfirmationDialogComponent } from '../../dialog/confirmation-dialog/confirmation-dialog.component';
import { localStorageKey } from '../../../helpers/constants';
import { Router } from '@angular/router';
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  @Input() transparent: boolean;
  isDialogOpen: boolean;
  isAdmin: boolean

  mentorshipMenuItems = [
      { name: "Mentors", route: "/mentorship/mentors" },
      { name: "Pending Requests", route: "/mentorship/pending-requests" },
      { name: "Upcoming Meetings", route: "/mentorship/upcoming-meetings" },
      { name: "Edit Account", route: "/my-account"}
    ];

  feedbackMenuItems = [
    { name: "Give Feedback", route: "/feedback/me" },
    { name: "All Feedback", route: "/feedback/subjects" }
  ];


  constructor(private dialog: MatDialog, private router: Router, private authService: AuthService) {
    this.transparent = false;
    this.isDialogOpen = false;
    this.isAdmin = false;
  }

  ngOnInit() {
    this.authService.isAdminUser().subscribe((isAdmin) => {
      this.isAdmin = isAdmin;
    });
  }

  hasTransparentBackground(): boolean {
    if (!this.transparent) {
      return false;
    }

    if (window.scrollY > window.innerHeight / 2 || this.isDialogOpen) {
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

    this.isDialogOpen = true;
    
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      this.isDialogOpen = false;

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

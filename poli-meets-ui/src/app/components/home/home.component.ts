import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AccountInformationDialogComponent } from '../dialog/account-information-dialog/account-information-dialog.component';
import { CoreService } from '../../services/core.service';

import * as AOS from 'aos';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  navbarHeight: number = 64;

  constructor(private dialog: MatDialog, private coreService: CoreService, private snackBar: MatSnackBar) { }

  ngOnInit() {
    window.addEventListener('load', () => {
      AOS.init();
    });

    this.coreService.account_completed()
      .subscribe((response) => {
        if (!response) {
          console.log("Must complete account information");
          this.openAccountInformationDialog();
        }
      });
  }

  scrollToCards(): void {
    window.scrollBy({
      top: window.innerHeight - this.navbarHeight,
      left: 0,
      behavior: "smooth",
    });
  }

  openAccountInformationDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.data = {
      name: '',
      surname: '',
      faculty: 0,
      year: '',
      cohort: '',
      group: ''
    }

    const dialogRef = this.dialog.open(AccountInformationDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(() => {
      this.snackBar.open('Successful account completion', undefined, {
        duration: 3000
      });
    });
  }
}

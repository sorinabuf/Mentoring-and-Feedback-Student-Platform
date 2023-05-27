import { Component } from '@angular/core';

import { localStorageKey } from '../../helpers/constants';
import { Router } from '@angular/router';

import * as AOS from 'aos';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.scss']
})
export class PageNotFoundComponent {
  constructor(private router: Router) { }

  ngOnInit() {
    window.addEventListener('load', () => {
      AOS.init();
    });
  }

  goToSite(): void {
    const token = localStorage.getItem(localStorageKey);

    if (!token) {
      this.router.navigate(['/login']);
    } else {
      this.router.navigate(['/home']);
    }
  }
}

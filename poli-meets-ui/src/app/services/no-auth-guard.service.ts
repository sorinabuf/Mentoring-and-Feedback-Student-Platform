import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { localStorageKey } from '../helpers/constants';

@Injectable({
  providedIn: 'root'
})
export class NoAuthGuardService implements CanActivate {
  constructor(private router: Router) { }

  canActivate(): boolean {
    const token = localStorage.getItem(localStorageKey);

    if (token) {
      this.router.navigate(['/home']);

      return false;
    }

    return true;
  }
}

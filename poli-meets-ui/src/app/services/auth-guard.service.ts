import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { localStorageKey } from '../helpers/constants';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  constructor(private router: Router) { }

  canActivate(): boolean {
    const token = localStorage.getItem(localStorageKey);

    if (!token) {
      this.router.navigate(['/login']);

      return false;
    }

    return true;
  }
}

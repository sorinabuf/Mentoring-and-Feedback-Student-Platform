import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { AuthService } from "./auth.service";

@Injectable({
    providedIn: 'root'
})
export class AdminGuardService implements CanActivate {

    isAdmin: boolean;

    constructor(private authService: AuthService) {
        this.isAdmin = false;
    }

    canActivate(): boolean {
        this.authService.isAdminUser().subscribe((isAdmin) => {
            this.isAdmin = isAdmin;

        });

        return this.isAdmin;
    }
}

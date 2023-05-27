import { Component, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, EMPTY } from 'rxjs';

import { AuthService } from '../../../services/auth.service';
import { localStorageKey } from '../../../helpers/constants';

import * as AOS from 'aos';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent {
  passwordVisibility: boolean;
  loginForm: FormGroup;
  failedLogin: boolean;
  isLoading: boolean;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.passwordVisibility = false;
    this.failedLogin = false;
    this.isLoading = false;

    this.loginForm = this.fb.group({
      email: ['', {
        validators: [
          Validators.required,
          Validators.email
        ],
      }
      ],
      password: ['', {
        validators: [
          Validators.required
        ],
      }
      ],
      rememberCredentials: [false]
    });
  }

  ngOnInit() {
    window.addEventListener('load', () => {
      AOS.init();
    });
  }

  onLogin(): void {
    this.loginForm.controls['email'].updateValueAndValidity();
    this.loginForm.controls['password'].updateValueAndValidity();

    if (this.loginForm.valid) {
      this.isLoading = true;
    }

    setTimeout(() => {
      if (this.loginForm.valid) {
        this.authService.login(
          this.loginForm.controls['email'].value,
          this.loginForm.controls['password'].value,
          this.loginForm.controls['rememberCredentials'].value)
          .pipe(
            catchError(() => {
              this.failedLogin = true;
              this.isLoading = false;
              console.error("Invalid credentials")

              return EMPTY;
            })
          )
          .subscribe((response) => {
            localStorage.setItem(localStorageKey, response.token);
            console.log("Login successful")

            this.router.navigate(["/home"]);
          });
      }
    }, 500); // fake traffic with timeout so loading spinner renders
  }

  toggleVisibility(): void {
    this.passwordVisibility = !this.passwordVisibility;
  }

  getPasswordType(): string {
    if (this.passwordVisibility) {
      return "text";
    }

    return "password";
  }

  getEmailErrorMessage(): string {
    if (this.loginForm.controls['email'].errors?.['required']) {
      return 'You must enter a value';
    }

    if (this.loginForm.controls['email'].errors?.['email']) {
      return 'You must enter a valid email address';
    }

    return '';
  }

  getPasswordErrorMessage(): string {
    if (this.loginForm.controls['password'].errors?.['required']) {
      return 'You must enter a value';
    }

    return '';
  }

  clearEmailErrors() {
    this.loginForm.controls['email'].setErrors(null);

    this.failedLogin = false;
  }

  clearPasswordErrors() {
    this.loginForm.controls['password'].setErrors(null);

    this.failedLogin = false;
  }
}

import { catchError, EMPTY, interval, startWith, Subject, switchMap, takeUntil } from 'rxjs';
import { Router } from '@angular/router';
import { Component, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';

import { AuthService } from '../../../services/auth.service';
import { PasswordValidator } from '../../../helpers/validators';

import * as AOS from 'aos';

const pollingActivateInterval = 5000;
const pollingActivateTimeout = 100000;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent {
  passwordVisibility: boolean;
  registerForm: FormGroup;
  failedRegister: boolean;
  successfulRegister: boolean;
  stopPollingActivate: Subject<any>;
  isLoading: boolean;

  ngOnInit() {
    window.addEventListener('load', () => {
      AOS.init();
    });
  }

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private snackBar: MatSnackBar, private location: Location) {
    this.passwordVisibility = false;
    this.failedRegister = false;
    this.successfulRegister = false;
    this.stopPollingActivate = new Subject();
    this.isLoading = false;

    this.registerForm = this.fb.group({
      email: ['', {
        validators: [
          Validators.required,
          Validators.email
        ],
      }
      ],
      password: ['', {
        validators: [
          Validators.required,
          PasswordValidator('')
        ]
      }
      ]
    });

    this.registerForm.controls['email'].valueChanges.subscribe(newEmail => {
      if (!this.registerForm.controls['email'].errors) {
        const atIndex = newEmail.indexOf('@');
        const username = newEmail.substring(0, atIndex);
        const newPasswordValidator = PasswordValidator(username);

        this.registerForm.controls['password'].setValidators([Validators.required, newPasswordValidator]);
      }
    });
  }

  onRegister(): void {
    this.registerForm.controls['email'].updateValueAndValidity();
    this.registerForm.controls['password'].updateValueAndValidity();

    if (this.registerForm.valid) {
      this.isLoading = true;
    }

    setTimeout(() => {
      if (this.registerForm.valid) {
        this.authService.register(
          this.registerForm.controls['email'].value,
          this.registerForm.controls['password'].value)
          .pipe(
            catchError(() => {
              this.failedRegister = true;
              this.isLoading = false;
              console.error("Username already exists");

              return EMPTY;
            })
          )
          .subscribe(() => {
            console.log("Continue with email verification");

            this.successfulRegister = true;

            interval(pollingActivateInterval)
              .pipe(
                startWith(0),
                switchMap(() => this.authService.is_activated(
                  this.registerForm.controls['email'].value
                )),
                takeUntil(this.stopPollingActivate)
              )
              .subscribe(response => {
                if (response) {
                  this.stopPollingActivate.next(0);
                  console.log("Account activated");

                  this.snackBar.open('Successful email activation', undefined, {
                    duration: 3000
                  });

                  this.router.navigate(["/login"]);
                }
              });

            setTimeout(() => {
              this.stopPollingActivate.next(0);

              if (this.location.path() === '/register') {
                console.log("Account activation page timeout");

                this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                  this.router.navigate(["/register"]);
                });
              }
            }, pollingActivateTimeout);
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
    if (this.registerForm.controls['email'].errors?.['required']) {
      return 'You must enter a value';
    }

    if (this.registerForm.controls['email'].errors?.['email']) {
      return 'You must enter a valid email address';
    }

    return '';
  }

  getPasswordErrorMessage(): string {
    if (this.registerForm.controls['password'].errors?.['required']) {
      return 'You must enter a value';
    }

    if (this.registerForm.controls['password'].errors?.['invalidPasswordLength']) {
      return 'Your password must be at least 6 characters long';
    }

    if (this.registerForm.controls['password'].errors?.['invalidPasswordUppercase']) {
      return 'Your password must contain at least 1 uppercase';
    }

    if (this.registerForm.controls['password'].errors?.['invalidPasswordLowercase']) {
      return 'Your password must contain at least 1 lowercase';
    }

    if (this.registerForm.controls['password'].errors?.['invalidPasswordDigit']) {
      return 'Your password must contain at least 1 digit';
    }

    if (this.registerForm.controls['password'].errors?.['invalidPasswordSpecialChar']) {
      return 'Your password must contain at least 1 special character (_#$%&^!?)';
    }

    if (this.registerForm.controls['password'].errors?.['invalidPasswordContains']) {
      return 'Your password can not contain your email';
    }

    return '';
  }

  clearEmailErrors(): void {
    this.registerForm.controls['email'].setErrors(null);

    this.failedRegister = false;
  }

  clearPasswordErrors(): void {
    this.registerForm.controls['password'].setErrors(null);

    this.failedRegister = false;
  }

  getPasswordInfo(): string {
    const passwordInfo = "Password must be at least 6 characters long and contain at least: 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character from the list [_#$%&^!?]."

    return passwordInfo;
  }
}

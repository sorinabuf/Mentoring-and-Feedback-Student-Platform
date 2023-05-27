import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function PasswordValidator(username: string): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
        const uppercaseLetterReg = /.*[A-Z].*/;
        const lowercaseLetterReg = /.*[a-z].*/;
        const digitReg = /.*[0-9].*/;
        const specialCharacterReg = /.*[_#$%&^!?].*/;

        const passwordValue: string = control.value;

        if (passwordValue.length < 6) {
            return { invalidPasswordLength: true };
        }

        if (!uppercaseLetterReg.test(passwordValue)) {
            return { invalidPasswordUppercase: true };
        }

        if (!lowercaseLetterReg.test(passwordValue)) {
            return { invalidPasswordLowercase: true };
        }

        if (!digitReg.test(passwordValue)) {
            return { invalidPasswordDigit: true };
        }

        if (!specialCharacterReg.test(passwordValue)) {
            return { invalidPasswordSpecialChar: true };
        }

        if (username && passwordValue.includes(username)) {
            return { invalidPasswordContains: true };
        }

        return null;
    }
}

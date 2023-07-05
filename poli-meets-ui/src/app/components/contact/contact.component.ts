import { Component, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoreService } from 'src/app/services/core.service';

import * as AOS from 'aos';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ContactComponent {
  contactForm: FormGroup;
  isLoading: boolean;

  maxMessageLength: number = 200;
  messageLength: number;

  constructor(private fb: FormBuilder, private coreService: CoreService, private snackBar: MatSnackBar) {
    this.isLoading = false;
    this.messageLength = 0;

    this.contactForm = this.fb.group({
      name: ['', {
        validators: [
          Validators.required,
        ],
      },
      ],
      email: ['', {
        validators: [
          Validators.required,
          Validators.email
        ],
      },
      ],
      message: ['', {
        validators: [
          Validators.required,
        ],
      },
      ]
    });
  }

  ngOnInit() {
    AOS.init();
    
    this.contactForm.controls['message'].valueChanges.subscribe(newMessage => {
      if (newMessage.length > this.maxMessageLength) {
        this.contactForm.controls['message'].setValue(newMessage.slice(0, this.maxMessageLength));
      } else {
        this.messageLength = newMessage.length;
      }
    });
  }

  onSend() : void {
    this.isLoading = true;

    this.coreService.send_contact_message(
      this.contactForm.controls["name"].value,
      this.contactForm.controls["email"].value,
      this.contactForm.controls["message"].value
    ).subscribe(_ => {
      this.isLoading = false;

      console.log("Message sent")

      this.snackBar.open('Your message was sent', undefined, {
        duration: 3000
      });

      this.contactForm.reset();
    });
  }
}

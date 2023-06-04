import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import * as AOS from 'aos';
import { Year } from 'src/app/models/year.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-my-feedback',
  templateUrl: './my-feedback.component.html',
  styleUrls: ['./my-feedback.component.scss']
})
export class MyFeedbackComponent {
  numDaysTillYearEnd : number;
  numSubmittedSubjectsForFeedback: number;
  numAvailableSubjectsForFeedback: number;
  faculty: string | undefined;
  specialty: string | undefined;
  studyCycle: string | undefined;
  year: string | undefined;

  constructor(private activatedRoute: ActivatedRoute, private feedbackService: FeedbackService) { 
    // TODO: use feedback service to fetch the numbers
    this.numDaysTillYearEnd = 0;
    this.numSubmittedSubjectsForFeedback = 0;
    this.numAvailableSubjectsForFeedback = 0;
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.faculty = student.universityYear.faculty.name;
      this.specialty = student.universityYear.faculty.domain;
      this.year = Year[student.universityYear.year as keyof typeof Year];

      if (this.year.includes("MASTER")) {
        this.studyCycle = "Master";
      } else {
        this.studyCycle = "License";
      }
    });

    this.feedbackService.getDaysUntilNextYear().subscribe((numDaysTillYearEnd) => {
      this.numDaysTillYearEnd = numDaysTillYearEnd;
    });

    this.feedbackService.countFeedbackLeft().subscribe((response) => {
      this.numSubmittedSubjectsForFeedback = response.countSubmitted;
      this.numAvailableSubjectsForFeedback = response.countActive;
    });

    AOS.init();
  }
}

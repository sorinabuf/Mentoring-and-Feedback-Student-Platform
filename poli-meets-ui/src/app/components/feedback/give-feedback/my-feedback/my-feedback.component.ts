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
  numSubjectsForFeedback: number;
  numAvailableSubjectsForFeedback: number;
  faculty: string | undefined;
  specialty: string | undefined;
  study_cicle: string | undefined;
  year: string | undefined;

  constructor(private activatedRoute: ActivatedRoute, private feedbackService: FeedbackService) { 
    // TODO: use feedback service to fetch the numbers
    this.numDaysTillYearEnd = 0;
    this.numSubjectsForFeedback = 0;
    this.numAvailableSubjectsForFeedback = 0;
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.faculty = student.universityYear.faculty.name;
      this.specialty = student.universityYear.faculty.domain;
      this.year = Year[student.universityYear.year as keyof typeof Year];

      if (this.year.includes("MASTER")) {
        this.study_cicle = "Master";
      } else {
        this.study_cicle = "License";
      }
    })

    AOS.init();
  }
}

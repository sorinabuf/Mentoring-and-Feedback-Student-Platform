import { Component } from '@angular/core';
import {FeedbackService} from "../../../../services/feedback.service";
import * as AOS from "aos";
import {FeedbackSubjectDetails} from "../../../../models/feedback/feedback-subject-details.model";
import {ActivatedRoute} from "@angular/router";
import {Teacher} from "../../../../models/teacher.model";

@Component({
  selector: 'app-subject-details',
  templateUrl: './subject-details.component.html',
  styleUrls: ['./subject-details.component.scss']
})
export class SubjectDetailsComponent {

    feedbackSubjectDetails: FeedbackSubjectDetails;

    chartOptions = {
        title: {
            text: "Basic Column Chart in Angular"
        },
        data: [{
            type: "column",
            dataPoints: [
                { label: "Apple",  y: 10  },
                { label: "Orange", y: 15  },
                { label: "Banana", y: 25  },
                { label: "Mango",  y: 30  },
                { label: "Grape",  y: 28  }
            ]
        }]
    };

    constructor(private feedbackService: FeedbackService, private route: ActivatedRoute) {
        this.feedbackSubjectDetails = new FeedbackSubjectDetails();
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.feedbackService.getFeedbackSubjectDetails(params['id'])
                .subscribe((feedbackSubjectDetails) => {
                    this.feedbackSubjectDetails = feedbackSubjectDetails;
                });
        });


        AOS.init();
    }

    getTeacherFullName(teacher: Teacher | undefined) {
        return teacher?.firstName + " " + teacher?.lastName;
    }

    getGradeRounded(grade?: number | undefined) {
        return Math.round(grade ?? 0);
    }

    getGradeWithOneDecimal(grade: number | undefined) {
        return grade?.toFixed(1);
    }

    getPercentageFeedbacks(partial: number | undefined, total: number | undefined) {
        if (partial === undefined || partial == 0) {
            return 0
        }

        return 100 * (total ?? 0) / partial;
    }
}

import {Component, ViewEncapsulation} from '@angular/core';
import * as AOS from "aos";
import {Subject} from "../../../../models/subject.model";
import {FeedbackService} from "../../../../services/feedback.service";
import {Semester} from "../../../../models/semester.model";
import {Year} from "../../../../models/year.model";
import {Teacher} from "../../../../models/teacher.model";
import {CategorySubjects} from "../../../../models/category-subjects.model";

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SubjectsComponent {
  activeSubjects: Subject[] | undefined;
  submittedSubjects: Subject[] | undefined;

  categories: CategorySubjects[] | undefined;

  constructor(private feedbackService: FeedbackService) {}

  ngOnInit() {
    this.feedbackService.getCategorySubjects()
        .subscribe( (categorySubjects) => {
          this.categories = categorySubjects;
        }
    )

    AOS.init();
  }

  getSemesterValue(semester: any) {
      switch (semester) {
          case "FIRST_SEMESTER":
              return Semester.FIRST_SEMESTER;
          case "SECOND_SEMESTER":
              return Semester.SECOND_SEMESTER;
          default:
              return "N/A"
      }
  }

    getYearValue(year: any) {
        switch (year) {
            case "YEAR_I_DEGREE":
                return "Year I Degree";
            case "YEAR_II_DEGREE":
                return "Year II Degree";
            case "YEAR_III_DEGREE":
                return "Year III Degree";
            case "YEAR_IV_DEGREE":
                return "Year IV Degree";
            default:
                return "N/A"
        }
    }

  getTeacherFullName(teacher: Teacher) {
      return teacher.firstName + " " + teacher.lastName;
  }
}

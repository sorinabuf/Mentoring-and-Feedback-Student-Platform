import {Component, Input} from '@angular/core';
import {FeedbackService} from "../../../../services/feedback.service";
import * as AOS from "aos";
import {FeedbackFaculty} from "../../../../models/feedback/feedback-faculty.model";
import {Semester} from "../../../../models/semester.model";
import {Teacher} from "../../../../models/teacher.model";

@Component({
  selector: 'app-all-subjects',
  templateUrl: './all-subjects.component.html',
  styleUrls: ['./all-subjects.component.scss']
})
export class AllSubjectsComponent {

  feedbackFaculty: FeedbackFaculty | undefined;

  seriesButton: any;

  constructor(private feedbackService: FeedbackService) {}

  ngOnInit() {
    this.feedbackService.getFeedbackFaculty()
        .subscribe( (feedbackFaculty) => {
              this.feedbackFaculty = feedbackFaculty;
            }
        )
  }

  getYears() {
    return new Set(this.feedbackFaculty?.years?.map(year => year.year));
  }

  getSeries(currentYear: string | undefined) {
    return this.feedbackFaculty?.years?.filter(year => year.year == currentYear).map(year => year.series);
  }

  getUniversityClasses(yearChosen: String | undefined, series: String) {
    return this.feedbackFaculty?.years?.filter(year => year.series == series && year.year == yearChosen).at(0)?.universityClasses;
  }

  getYearValue(year: any) {
    switch (year) {
      case "YEAR_I_DEGREE":
        return "I";
      case "YEAR_II_DEGREE":
        return "II";
      case "YEAR_III_DEGREE":
        return "III";
      case "YEAR_IV_DEGREE":
        return "IV";
      default:
        return "N/A"
    }
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

  getTeacherFullName(teacher: Teacher | undefined) {
    return teacher?.firstName + " " + teacher?.lastName;
  }

  getGradeRounded(grade?: number | undefined) {
    return Math.round(grade ?? 0);
  }

  getGradeWithOneDecimal(grade: number | undefined) {
    return grade?.toFixed(1);
  }

}

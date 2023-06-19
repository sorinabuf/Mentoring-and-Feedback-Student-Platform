import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Feedback} from "../../../../models/feedback.model";
import {FeedbackService} from "../../../../services/feedback.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TeachingAssistant} from "../../../../models/teaching-assistant.model";
import {Subject} from "../../../../models/subject.model";
import {Teacher} from "../../../../models/teacher.model";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ConfirmationDialogComponent} from "../../../dialog/confirmation-dialog/confirmation-dialog.component";
import {localStorageKey} from "../../../../helpers/constants";
import {FeedbackDialogComponent} from "../../../dialog/feedback-dialog/feedback-dialog.component";
import {Category} from "../../../../models/category.model";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent {
  form: FormGroup;

  feedback: Feedback;

  options: { value: string, label: string }[] = [
    { value: 'GRADE_5', label: 'Strongly Agree' },
    { value: 'GRADE_4', label: 'Agree' },
    { value: 'GRADE_3', label: 'Neutral' },
    { value: 'GRADE_2', label: 'Disagree' },
    { value: 'GRADE_1', label: 'Strongly Disagree' }
  ];

  optionsDifficulty: { value: string, label: string }[] = [
    { value: 'GRADE_5', label: 'Very Difficult' },
    { value: 'GRADE_4', label: 'Difficult' },
    { value: 'GRADE_3', label: 'Moderate' },
    { value: 'GRADE_2', label: 'Easy' },
    { value: 'GRADE_1', label: 'Very Easy' }
  ];

  universityClass: Subject | undefined;

  category: Category | undefined;

  selectedOption: string | undefined;

  teachingAssistants: { value: number, label: string }[];

  isDialogOpen: boolean;


  constructor(private fb: FormBuilder, private feedbackService: FeedbackService,
              private route: ActivatedRoute, private dialog: MatDialog, private router: Router) {
    this.feedback = new Feedback();
    this.teachingAssistants = [];
    this.isDialogOpen = false;


    this.form = this.fb.group({
      grade: ['', Validators.required],
      feedbackText: [''],
    });
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.feedbackService.getSubject(params['subjectId']).subscribe( (universityClass) => {
        this.universityClass = universityClass;
      });

      this.feedbackService.getCategory(params['categoryId']).subscribe( (category) => {
        this.category = category;
      });

    });
  }

  onSubmit() {
    if (this.form.valid) {
      const formValues = this.form.value;

      this.feedback.grade = formValues.grade;
      this.feedback.feedbackText = formValues.feedbackText;
      this.feedback.universityClassId = this.universityClass?.id;
      this.feedback.categoryId = this.category?.id;


      this.feedbackService.postFeedback(this.feedback).subscribe();
      console.log(this.feedback);

    } else {

      console.log('Form is invalid');
    }
  }

  openFeedbackDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;

    dialogConfig.data = {
      title: 'Feedback Submitted',
      message: 'Thank you for your feedback.'
    };

    this.isDialogOpen = true;

    const dialogRef = this.dialog.open(FeedbackDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      this.isDialogOpen = false;

        this.router.navigate(["/feedback/me/subjects"]);

    });
  }

  getTeacherFullName(teacher: Teacher | undefined) {
    return teacher?.firstName + " " + teacher?.lastName;
  }

  getOptions() {
    if (this.category?.name == "Difficulty") {
      return this.optionsDifficulty;
    } else {
      return this.options;
    }
  }

}

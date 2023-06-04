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

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent {
  form: FormGroup;

  feedback: Feedback;

  options: { value: string, label: string }[] = [
    { value: 'STRONGLY_AGREE', label: 'Strongly Agree' },
    { value: 'AGREE', label: 'Agree' },
    { value: 'NEUTRAL', label: 'Neutral' },
    { value: 'DISAGREE', label: 'Disagree' },
    { value: 'STRONGLY_DISAGREE', label: 'Strongly Disagree' }
  ];

  optionsDifficulty: { value: string, label: string }[] = [
    { value: 'VERY_DIFFICULT', label: 'Very Difficult' },
    { value: 'DIFFICULT', label: 'Difficult' },
    { value: 'MODERATE', label: 'Moderate' },
    { value: 'EASY', label: 'Easy' },
    { value: 'VERY_EASY', label: 'Very Easy' }
  ];

  universityClass: Subject | undefined;

  selectedOption: string | undefined;

  teachingAssistants: { value: number, label: string }[];

  isDialogOpen: boolean;


  constructor(private fb: FormBuilder, private feedbackService: FeedbackService,
              private route: ActivatedRoute, private dialog: MatDialog, private router: Router) {
    this.feedback = new Feedback();
    this.teachingAssistants = [];
    this.isDialogOpen = false;


    this.form = this.fb.group({
      gradeCourse: ['', Validators.required],
      gradeLaboratory: ['', Validators.required],
      gradeHomework: ['', Validators.required],
      gradeExam: ['', Validators.required],
      gradeDifficulty: ['', Validators.required],
      feedbackCourse: [''],
      feedbackLaboratory: [''],
      feedbackDuringSemester: [''],
      feedbackExam: [''],
      feedbackDifficulty: [''],
      teachingAssistant: ['']
    });
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.feedbackService.getSubject(params['id']).subscribe( (universityClass) => {
        this.universityClass = universityClass;
      });

      this.feedbackService.getTeachingAssistants(params['id']).subscribe( (teachingAssistants) => {
        this.teachingAssistants = teachingAssistants.map((ta: { id: { toString: () => any; }; firstName: string; lastName: string; }) => {
          return {
            value: ta.id,
            label: ta.firstName + ' ' + ta.lastName
          };
        })
      }
    )
    });

  }

  onSubmit() {
    if (this.form.valid) {
      const formValues = this.form.value;

      this.feedback.gradeCourse = formValues.gradeCourse;
      this.feedback.gradeLaboratory = formValues.gradeLaboratory;
      this.feedback.gradeHomework = formValues.gradeHomework;
      this.feedback.gradeExam = formValues.gradeExam;
      this.feedback.gradeDifficulty = formValues.gradeDifficulty;
      this.feedback.feedbackCourse = formValues.feedbackCourse;
      this.feedback.feedbackLaboratory = formValues.feedbackLaboratory;
      this.feedback.feedbackDuringSemester = formValues.feedbackDuringSemester;
      this.feedback.feedbackExam = formValues.feedbackExam;
      this.feedback.feedbackDifficulty = formValues.feedbackDifficulty;
      this.feedback.universityClassId = this.universityClass?.id;
      this.feedback.teachingAssistantId = formValues.teachingAssistant;

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

}

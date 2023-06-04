import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EMPTY, catchError } from 'rxjs';
import { compareArrays } from 'src/app/helpers/methods';
import { MentorInfo } from 'src/app/models/mentor-info.model';
import { Skill } from 'src/app/models/skill.model';
import { Subject } from 'src/app/models/subject-simple.model';
import { MentorshipService } from 'src/app/services/mentorship.service';

@Component({
  selector: 'app-mentor-dialog',
  templateUrl: './mentor-dialog.component.html',
  styleUrls: ['./mentor-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MentorDialogComponent {
  mentorInfoForm: FormGroup;
  isMentor: boolean;
  skills: Skill[];
  subjects: Subject[];
  descriptionLength: number;
  mentorId: number;
  initialData: any;
  isChangedDescription: boolean;
  areChangedSkills: boolean;
  areChangedSubjects: boolean;

  maxDescriptionLength = 200;

  constructor(private fb: FormBuilder, private mentorshipService: MentorshipService, private dialogRef: MatDialogRef<MentorDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: { isMentor: boolean, mentorId: number, description: string, skills: Skill[], subjects: Subject[] }) {
    this.initialData = data;
    this.isMentor = data.isMentor;
    this.mentorId = data.mentorId;

    let defaultDescription = '';
    let defaultSkills: number[] = [];
    let defaultSubjects: number[] = [];

    if (this.isMentor) {
      defaultDescription = data.description;
      defaultSkills = data.skills.map(skill => skill.id);
      defaultSubjects = data.subjects.map(subject => subject.id);
    }

    this.mentorInfoForm = this.fb.group({
      description: [defaultDescription, {
        validators: [
          Validators.required,
        ],
      },
      ],
      skills: [defaultSkills, {
        validators: [
          Validators.required,
        ],
      },
      ],
      subjects: [defaultSubjects, {
        validators: [
          Validators.required,
        ],
      },
      ]
    });

    this.skills = [];
    this.subjects = [];
    this.descriptionLength = defaultDescription.length;

    this.isChangedDescription = false;
    this.areChangedSkills = false;
    this.areChangedSubjects = false;
  }

  ngOnInit() {
    this.mentorshipService.get_mentor_possible_subjects()
      .subscribe((response) => {
        this.subjects = response;
      });

    this.mentorshipService.get_skills()
      .subscribe((response) => {
        this.skills = response;
      });

    this.mentorInfoForm.controls['description'].valueChanges.subscribe(newDescription => {
      if (newDescription.length > this.maxDescriptionLength) {
        this.mentorInfoForm.controls['description'].setValue(newDescription.slice(0, this.maxDescriptionLength));
      } else {
        this.descriptionLength = newDescription.length;
      }

      if (this.isMentor) {
        if (newDescription != this.data.description) {
          this.isChangedDescription = true;
        } else {
          this.isChangedDescription = false;
        }
      }
    });

    this.mentorInfoForm.controls['skills'].valueChanges.subscribe(newSkills => {
      if (this.isMentor) {
        if (!compareArrays(newSkills, this.data.skills.map(skill => skill.id))) {
          this.areChangedSkills = true;
        } else {
          this.areChangedSkills = false;
        }
      }
    });

    this.mentorInfoForm.controls['subjects'].valueChanges.subscribe(newSubjects => {
      if (this.isMentor) {
        if (!compareArrays(newSubjects, this.data.subjects.map(subject => subject.id))) {
          this.areChangedSubjects = true;
        } else {
          this.areChangedSubjects = false;
        }
      }
    });
  }

  onMentorInfoSubmit(): void {
    const selected_skill_ids = this.mentorInfoForm.controls['skills'].value;
    const selected_subject_ids = this.mentorInfoForm.controls['subjects'].value

    let mentorInfo = new MentorInfo(this.mentorId,
      this.mentorInfoForm.controls['description'].value,
      this.skills.filter(skill => selected_skill_ids.includes(skill.id)),
      this.subjects.filter(subject => selected_subject_ids.includes(subject.id)), undefined
    );

    if (!this.isMentor) {
      this.mentorshipService.create_mentor(mentorInfo).subscribe(
        (response) => {
          mentorInfo.id = response.id;
          this.dialogRef.close(mentorInfo);
        });
    } else {
      this.mentorshipService.update_mentor(mentorInfo).subscribe(
        () => {
          this.dialogRef.close(mentorInfo);
        });
    }
  }
}

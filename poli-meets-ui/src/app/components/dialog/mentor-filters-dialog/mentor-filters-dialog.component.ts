import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Skill } from 'src/app/models/skill.model';
import { UniversityClass } from 'src/app/models/university-class.model';

@Component({
  selector: 'app-mentor-filters-dialog',
  templateUrl: './mentor-filters-dialog.component.html',
  styleUrls: ['./mentor-filters-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MentorFiltersDialogComponent {
  availabilities = ['Today', 'In a week', 'In a month'];

  skills: Skill[];
  subjects: UniversityClass[];
  enabledSkills: number[];
  enabledSubjects: number[];

  filterAvailability: string;
  filterSkillsIds: Set<number>
  filterSubjectsIds: Set<number>;

  constructor(@Inject(MAT_DIALOG_DATA) public data: { skills: Skill[], subjects: UniversityClass[], enabledSkills: number[], enabledSubjects: number[],  filters: { [key: string]: any}}) {
    this.skills = data.skills;
    this.subjects = data.subjects;
    this.enabledSkills = data.enabledSkills;
    this.enabledSubjects = data.enabledSubjects;

    this.filterAvailability = data.filters["availability"];
    this.filterSkillsIds = new Set(data.filters["skills"]);
    this.filterSubjectsIds = new Set(data.filters["subjects"]);
  }

  availabilityChanged(event: any, availability: string) : void {
    if (event.selected) {
      this.filterAvailability = availability;
    } else {
      this.filterAvailability = "";
    }
  }

  disableSkill(id: number) : boolean {
    return !this.enabledSkills.includes(id);
  }

  disableSubject(id: number): boolean {
    return !this.enabledSubjects.includes(id);
  }

  skillCheck(event: any, id: number): void {
    if (event.checked) {
      this.filterSkillsIds.add(id);
    } else {
      this.filterSkillsIds.delete(id);
    }
  }

  subjectCheck(event : any, id : number): void {
    if (event.checked) {
      this.filterSubjectsIds.add(id);
    } else {
      this.filterSubjectsIds.delete(id);
    }
  }

  isAvailabilitySelected(availability : string) : boolean {
    return availability == this.filterAvailability;
  }

  isSkillChecked(id: number): boolean {
    return this.filterSkillsIds.has(id);
  }

  isSubjectChecked(id : number) : boolean {
    return this.filterSubjectsIds.has(id);
  }

  resetFilters() : void {
    this.filterAvailability = "";
    this.filterSkillsIds.clear();
    this.filterSubjectsIds.clear();
  }

  getFilters() : any {
    return {
      availability: this.filterAvailability,
      skills: this.filterSkillsIds,
      subjects: this.filterSubjectsIds
    };
  }
}

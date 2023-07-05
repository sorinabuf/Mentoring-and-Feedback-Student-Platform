import { Component, ElementRef, HostListener, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MentorFiltersDialogComponent } from '../../dialog/mentor-filters-dialog/mentor-filters-dialog.component';
import { Skill } from 'src/app/models/skill.model';
import { MentorshipService } from 'src/app/services/mentorship.service';
import { UniversityClass } from 'src/app/models/university-class.model';
import { MentorInfo } from 'src/app/models/mentor-info.model';
import { MeetingSlot } from 'src/app/models/meeting-slot.model';
import { BookMeetingDialogComponent } from '../../dialog/book-meeting-dialog/book-meeting-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

import * as AOS from 'aos';

@Component({
  selector: 'app-mentors',
  templateUrl: './mentors.component.html',
  styleUrls: ['./mentors.component.scss', '../mentorship-header.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MentorsComponent {
  @ViewChild('mentors') mentorsHeader!: ElementRef;
  scrollPosition: number = 0;

  skills: Skill[];
  subjects: UniversityClass[];
  mentors: MentorInfo[];
  mentorsSlots: { [key: number]: MeetingSlot[] };
  filteredMentors: MentorInfo[];
  mentorsSkills: number[];
  mentorsSubjects: number[];
  filters: { [key: string]: any };
  showDetails: { [key: number]: boolean };
  searchName: string;
  isLoading: boolean;
  countLoads: number;

  constructor(private dialog: MatDialog, private mentorshipService: MentorshipService, private snackBar: MatSnackBar) {
    this.skills = [];
    this.subjects = [];
    this.mentors = [];
    this.mentorsSlots = {};
    this.filteredMentors = [];
    this.mentorsSkills = [];
    this.mentorsSubjects = [];
    this.filters = {
      availability: "",
      skills: new Set(),
      subjects: new Set()
    };
    this.showDetails = {};
    this.searchName = '';
    this.isLoading = false;
    this.countLoads = 0;
  }

  ngOnInit(): void {
    AOS.init();

    this.mentorshipService.get_skills().subscribe((response) => {
      this.skills = response;
    });

    this.mentorshipService.get_mentor_filter_subjects().subscribe((response) => {
      this.subjects = response;
    });

    this.isLoading = true;

    setTimeout(() => {
      this.mentorshipService.get_all_mentors().subscribe((response) => {
        this.mentors = response.filter(mentor => mentor.subjects.map(subject => subject.id).some(subject => new Set(this.subjects.map(subject => subject.id)).has(subject)));
        this.filteredMentors = this.mentors;

        console.log(this.mentors);

        this.mentorsSkills = [... new Set(this.mentors.map(mentor => mentor.skills).flatMap(list => list).map(skill => skill.id))];
        this.mentorsSubjects = [... new Set(this.mentors.map(mentor => mentor.subjects).flatMap(list => list).map(subject => subject.id))];

        this.mentors.forEach(mentor => {
          this.mentorshipService.get_mentor_free_slots(mentor.id).subscribe(response => {
            this.mentorsSlots[mentor.id] = response;
            this.showDetails[mentor.id] = false;

            this.countLoads++;

            if (this.countLoads == this.mentors.length) {
              this.countLoads = 0;
              this.isLoading = false;
            }
          });
        });
      });
    }, 200);
  }

  openFiltersDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.data = {
      skills: this.skills,
      subjects: this.subjects,
      enabledSkills: this.mentorsSkills,
      enabledSubjects: this.mentorsSubjects,
      filters: this.filters
    }

    const dialogRef = this.dialog.open(MentorFiltersDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((response) => {
      if (response) {
        this.filters = response;
        this.filterMentors();
      }
    });
  }

  getMeetingSlots(mentor: MentorInfo): number {
    return this.mentorsSlots[mentor.id].length;
  }

  openBookingMeetingDialog(mentor: MentorInfo): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.data = {
      subjects: mentor.subjects,
      meetings: this.mentorsSlots[mentor.id]
    }

    const dialogRef = this.dialog.open(BookMeetingDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((response) => {
      if (response) {
        console.log('Successful meeting request');

        this.snackBar.open('Meeting request sent', undefined, {
          duration: 3000
        });

        this.mentorshipService.get_mentor_free_slots(mentor.id).subscribe(response => {
          this.mentorsSlots[mentor.id] = response;
        });
      }
    });
  }

  getSkillById(id: number): string {
    return this.skills.filter(skill => skill.id == id)[0].name;
  }

  getSubjectById(id: number): string {
    return this.subjects.filter(subject => subject.id == id)[0].name;
  }

  deleteAvailabilityFilter(): void {
    this.filters['availability'] = '';
    this.filterMentors();
  }

  deleteSkillFilter(filter: number): void {
    this.filters['skills'].delete(filter);
    this.filterMentors();
  }

  deleteSubjectFilter(filter: number): void {
    this.filters['subjects'].delete(filter);
    this.filterMentors();
  }

  resetFilters(): void {
    this.filters = {
      availability: "",
      skills: new Set(),
      subjects: new Set()
    };
    this.filteredMentors = this.mentors;
    Object.keys(this.showDetails).forEach(key => this.showDetails[+key] = false);
  }

  hasActiveFilters(): boolean {
    return this.filters['availability'] || this.filters['skills'].size || this.filters['subjects'].size;
  }

  filterMentors(): void {
    this.filteredMentors = [];

    setTimeout(() => {
      this.filteredMentors = this.mentors;

      Object.keys(this.showDetails).forEach(key => this.showDetails[+key] = false);

      if (this.searchName) {
        this.filteredMentors = this.filteredMentors.filter(mentor => {
          let name = mentor.student?.firstName + ' ' + mentor.student?.lastName;

          console.log(name);

          return name.toLowerCase().includes(this.searchName.toLowerCase());
        }
        );
      }

      if (this.filters['skills'].size) {
        this.filteredMentors = this.filteredMentors.filter(mentor => mentor.skills.map(skill => skill.id).some(skill => this.filters['skills'].has(skill)));
      }

      if (this.filters['subjects'].size) {
        this.filteredMentors = this.filteredMentors.filter(mentor => mentor.subjects.map(subject => subject.id).some(subject => this.filters['subjects'].has(subject)));
      }

      if (this.filters['availability'] && this.filteredMentors.length) {
        if (this.filters['availability'] == 'Today') {
          this.filteredMentors = this.filteredMentors.filter(mentor => this.mentorsSlots[mentor.id].map(slot => new Date(slot.date)).some(date => this.isDateToday(date)));
        } else if (this.filters['availability'] == 'In a week') {
          this.filteredMentors = this.filteredMentors.filter(mentor => this.mentorsSlots[mentor.id].map(slot => new Date(slot.date)).some(date => this.isDateInNext7Days(date)));
        } else if (this.filters['availability'] == 'In a month') {
          this.filteredMentors = this.filteredMentors.filter(mentor => this.mentorsSlots[mentor.id].map(slot => new Date(slot.date)).some(date => this.isDateInNextMonth(date)));
        }
      }

      console.log(this.filteredMentors);
    }, 50);
  }

  isDateToday(date: Date): boolean {
    const today = new Date();

    return date.getDate() === today.getDate() && date.getMonth() === today.getMonth() && date.getFullYear() === today.getFullYear();
  }

  isDateInNext7Days(date: Date): boolean {
    const currentDate = new Date();
    const timeDifference = date.getTime() - currentDate.getTime();
    const daysDifference = timeDifference / (1000 * 60 * 60 * 24);

    return daysDifference <= 7;
  }

  isDateInNextMonth(date: Date): boolean {
    const currentDate = new Date();
    const timeDifference = date.getTime() - currentDate.getTime();
    const daysDifference = timeDifference / (1000 * 60 * 60 * 24);

    return daysDifference <= 30;
  }

  getImage(mentor: MentorInfo): string {
    return "data:image/png;base64," + mentor.student?.image;
  }

  getMentorName(mentor: MentorInfo): string {
    return mentor.student?.firstName + ' ' + mentor.student?.lastName;
  }

  getMentorMail(mentor: MentorInfo): string | undefined {
    return mentor.student?.personalEmail;
  }

  getMentorStudentInfo(mentor: MentorInfo): string {
    let license = 'License';

    if (mentor.student?.universityYear.year.includes('MASTER')) {
      license = 'Master';
    }

    const year = mentor.student!.universityYear.year;
    const startIndex = year.indexOf("_") + 1;
    const endIndex = year.lastIndexOf("_");
    const yearValue = year.substring(startIndex, endIndex);

    return license + ' ' + yearValue + ', ' + mentor.student!.groupNum + mentor.student!.universityYear.series;
  }

  getMentorNumBookingSlots(mentor: MentorInfo): number {
    return this.mentorsSlots[mentor.id] ? this.mentorsSlots[mentor.id].length : 0;
  }

  getMentorNumSubjects(mentor: MentorInfo): number {
    return mentor.subjects.length;
  }

  toggleShowDetails(mentor: MentorInfo): void {
    this.showDetails[mentor.id] = !this.showDetails[mentor.id];
  }

  onSearch(): void {
    this.filterMentors();
  }

  clearSearch(): void {
    this.searchName = '';
    this.onSearch();
  }

  scrollToMentorsSection(): void {
    const timelineSectionHeight = this.mentorsHeader.nativeElement.clientHeight;
    const offsetTop = this.mentorsHeader.nativeElement.offsetTop;
    const scrollPosition = offsetTop - timelineSectionHeight + 64;

    window.scrollTo({
      top: scrollPosition,
      behavior: "smooth"
    });
  }

  displayScrollButton(): boolean {
    if (this.mentorsHeader && this.scrollPosition > this.mentorsHeader.nativeElement.offsetTop) {
      return true;
    }

    return false;
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(_: Event) {
    this.scrollPosition = window.pageYOffset;
  }
}

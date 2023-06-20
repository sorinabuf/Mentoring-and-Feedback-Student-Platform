import { Component, QueryList, ViewChildren, ViewEncapsulation } from '@angular/core';
import { Faculty } from "../../models/faculty.model";
import { CoreService } from "../../services/core.service";
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UniversityYear } from 'src/app/models/university-year-info.model';
import { Year } from 'src/app/models/year.model';
import { UniversityClass } from 'src/app/models/university-class-info.model';
import { Semester } from 'src/app/models/semester.model';
import { Teacher } from 'src/app/models/teacher.model';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../dialog/confirmation-dialog/confirmation-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FacultyAdminDialogComponent } from '../dialog/admin/faculty-admin-dialog/faculty-admin-dialog.component';
import { UniversityYearAdminDialogComponent } from '../dialog/admin/university-year-admin-dialog/university-year-admin-dialog.component';
import { FeedbackService } from "../../services/feedback.service";
import { Category } from "../../models/category.model";
import { CategoryAdminDialogComponent } from "../dialog/admin/category-admin-dialog/category-admin-dialog.component";
import { TeacherAdminDialogComponent } from '../dialog/admin/teacher-admin-dialog/teacher-admin-dialog.component';
import { UniversityClassAdminDialogComponent } from '../dialog/admin/university-class-admin-dialog/university-class-admin-dialog.component';


@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.scss'],
    encapsulation: ViewEncapsulation.None
})

export class AdminComponent {
    @ViewChildren(MatPaginator) paginator!: QueryList<MatPaginator>;
    @ViewChildren(MatSort) sort!: QueryList<MatSort>;
    selectedTabIndex: number;

    displayedColumnsFaculty: string[] = ['name', 'domain', 'description', 'actions'];
    displayedColumnsUniversityYears: string[] = ['facultyName', 'yearValue', 'series', 'actions'];
    displayedColumnsUniversityClasses: string[] = ['name', 'abbreviation', 'description', 'teacher', 'universityYear', 'semester', 'actions'];
    displayedColumnsTeachers: string[] = ['firstName', 'lastName', 'actions'];
    displayedColumnsCategories: string[] = ['categoryName', 'gradeQuestion', 'textQuestion', 'actions'];
    faculties: MatTableDataSource<Faculty>;
    universityYears: MatTableDataSource<UniversityYear>;
    universityClasses: MatTableDataSource<UniversityClass>;
    teachers: MatTableDataSource<Teacher>;
    categories: MatTableDataSource<Category>;

    allFaculties: Faculty[];
    allTeachers: Teacher[];
    allUniversityYears: UniversityYear[];

    ngAfterViewInit() {
        this.faculties.paginator = this.paginator.toArray()[0];
        this.faculties.sort = this.sort.toArray()[0];

        this.universityYears.paginator = this.paginator.toArray()[1];
        this.universityYears.sort = this.sort.toArray()[1];

        this.universityClasses.paginator = this.paginator.toArray()[2];
        this.universityClasses.sort = this.sort.toArray()[2];

        this.teachers.paginator = this.paginator.toArray()[3];
        this.teachers.sort = this.sort.toArray()[3];

        this.categories.paginator = this.paginator.toArray()[4];
        this.categories.sort = this.sort.toArray()[4];
    }

    constructor(private coreService: CoreService,
        private feedbackService: FeedbackService,
        private dialog: MatDialog,
        private snackBar: MatSnackBar) {
        this.faculties = new MatTableDataSource();
        this.universityYears = new MatTableDataSource();
        this.universityClasses = new MatTableDataSource();
        this.teachers = new MatTableDataSource();
        this.categories = new MatTableDataSource();
        this.selectedTabIndex = 0;
        this.allFaculties = [];
        this.allTeachers = [];
        this.allUniversityYears = [];
    }

    ngOnInit() {
        this.initFaculties();
        this.initTeachers();
        this.initCategories();
    }

    initFaculties(): void {
        this.coreService.get_all_faculties().subscribe(faculties => {
            this.faculties.data = faculties.reverse();
            this.allFaculties = faculties.sort((a, b) => {
                let result = a.name.localeCompare(b.name);

                if (!result) {
                    result = a.domain.localeCompare(b.domain);
                }

                return result;
            });

            this.initUniversityYears();
        });
    }

    initUniversityYears(): void {
        this.coreService.get_all_university_years().subscribe(universityYears => {
            this.universityYears.data = universityYears.reverse().map(element => { return { ...element, facultyName: this.getFacultyNameDomain(element), yearValue: this.getYear(element.year) } });
            this.allUniversityYears = universityYears.sort((a, b) => {
                let result = a.faculty.name.localeCompare(b.faculty.name);

                if (!result) {
                    result = a.faculty.domain.localeCompare(b.faculty.domain);
                }

                if (!result) {
                    result = a.year.localeCompare(b.year);
                }

                if (!result) {
                    result = a.series.localeCompare(b.series);
                }

                return result;
            });

            this.initUniversityClasses();
        });
    }

    initUniversityClasses(): void {
        this.coreService.get_all_university_classes().subscribe(universityClasses => {
            this.universityClasses.data = universityClasses.reverse();
        });
    }

    initTeachers(): void {
        this.coreService.get_all_teachers().subscribe(teachers => {
            this.teachers.data = teachers.reverse();
            this.allTeachers = teachers.sort((a, b) => {
                let result = a.firstName.localeCompare(b.firstName);

                if (!result) {
                    result = a.lastName.localeCompare(b.lastName);
                }

                return result;
            });

            this.initUniversityClasses();
        });
    }

    initCategories(): void {
        this.feedbackService.getCategories().subscribe(categories => {
            this.categories.data = categories;
        });
    }

    getYear(year: string): string {
        return Year[year as keyof typeof Year];
    }

    getSemester(semester: string): string {
        return Semester[semester as keyof typeof Semester];
    }

    getUniversityYear(universityYear: UniversityYear): string {
        return universityYear.faculty.name + ' - ' + universityYear.faculty.domain + ' - ' + this.getYear(universityYear.year) + ' - ' + universityYear.series;
    }

    getFacultyNameDomain(universityYear: UniversityYear): string {
        return universityYear.faculty.name + ' - ' + universityYear.faculty.domain;
    }

    openAddDialog(): void {
        switch (this.selectedTabIndex) {
            case 0: {
                this.openFacultyDialog(undefined);
                break;
            }

            case 1: {
                this.openUniversityYearDialog(undefined);
                break;
            }

            case 2: {
                this.openUniversityClassDialog(undefined);
                break;
            }

            case 3: {
                this.openTeacherDialog(undefined);
                break;
            }

            case 4: {
                this.openCategoryDialog(undefined);
                break;
            }
        }
    }

    openFacultyDialog(faculty: Faculty | undefined): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.data = {
            faculty: faculty
        }

        const dialogRef = this.dialog.open(FacultyAdminDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe((response) => {
            if (response) {
                if (!faculty) {
                    this.coreService.add_faculty(response).subscribe(_ => {
                        this.initFaculties();

                        console.log("Added faculty");

                        this.snackBar.open('Successful faculty addition', undefined, {
                            duration: 3000
                        });
                    });
                } else {
                    this.coreService.update_faculty(response).subscribe(_ => {
                        this.initFaculties();

                        console.log("Updated faculty");

                        this.snackBar.open('Successful faculty update', undefined, {
                            duration: 3000
                        });
                    });
                }
            }
        });
    }

    openUniversityYearDialog(universityYear: UniversityYear | undefined): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.data = {
            universityYear: universityYear,
            faculties: this.allFaculties
        }

        const dialogRef = this.dialog.open(UniversityYearAdminDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe((response) => {
            if (response) {
                if (!universityYear) {
                    this.coreService.add_university_year(response).subscribe(_ => {
                        this.initUniversityYears();

                        console.log("Added university year");

                        this.snackBar.open('Successful university year addition', undefined, {
                            duration: 3000
                        });
                    });
                } else {
                    this.coreService.update_university_year(response).subscribe(_ => {
                        this.initUniversityYears();

                        console.log("Updated university year");

                        this.snackBar.open('Successful university year update', undefined, {
                            duration: 3000
                        });
                    });
                }
            }
        });
    }

    openUniversityClassDialog(universityClass: UniversityClass | undefined): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.data = {
            universityClass: universityClass,
            teachers: this.allTeachers,
            universityYears: this.allUniversityYears
        }

        const dialogRef = this.dialog.open(UniversityClassAdminDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe((response) => {
            if (response) {
                if (!universityClass) {
                    this.coreService.add_university_class(response).subscribe(_ => {
                        this.initUniversityClasses();

                        console.log("Added university class");

                        this.snackBar.open('Successful university class addition', undefined, {
                            duration: 3000
                        });
                    });
                } else {
                    this.coreService.update_university_class(response).subscribe(_ => {
                        this.initUniversityClasses();

                        console.log("Updated university class");

                        this.snackBar.open('Successful university class update', undefined, {
                            duration: 3000
                        });
                    });
                }
            }
        });
    }

    openTeacherDialog(teacher: Teacher | undefined): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.data = {
            teacher: teacher
        }

        const dialogRef = this.dialog.open(TeacherAdminDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe((response) => {
            if (response) {
                if (!teacher) {
                    this.coreService.add_teacher(response).subscribe(_ => {
                        this.initTeachers();

                        console.log("Added teacher");

                        this.snackBar.open('Successful teacher addition', undefined, {
                            duration: 3000
                        });
                    });
                } else {
                    this.coreService.update_teacher(response).subscribe(_ => {
                        this.initTeachers();

                        console.log("Updated teacher");

                        this.snackBar.open('Successful teacher update', undefined, {
                            duration: 3000
                        });
                    });
                }
            }
        });
    }

    openCategoryDialog(category: Category | undefined): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.data = {
            category: category
        }

        const dialogRef = this.dialog.open(CategoryAdminDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe((response) => {
            if (response) {
                if (!category) {
                    this.feedbackService.addCategory(response).subscribe(_ => {
                        this.initCategories();

                        console.log("Added feedback category");

                        this.snackBar.open('Successful feedback category addition', undefined, {
                            duration: 3000
                        });
                    });
                } else {
                    this.feedbackService.updateCategory(response).subscribe(_ => {
                        this.initCategories();

                        console.log("Updated feedback category");

                        this.snackBar.open('Successful feedback category update', undefined, {
                            duration: 3000
                        });
                    });
                }
            }
        });
    }

    openFacultyDeleteDialog(faculy: Faculty): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;

        dialogConfig.data = {
            title: 'Delete faculty',
            message: 'Are you sure you want to delete this faculty?'
        };

        const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(result => {
            if (result === "yes") {
                this.coreService.delete_faculty(faculy.id).subscribe(_ => {
                    this.initFaculties();

                    console.log("Deleted faculty");

                    this.snackBar.open('Successful faculty deletion', undefined, {
                        duration: 3000
                    });
                });
            }
        });
    }

    openCategoryDeleteDialog(category: Category): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;

        dialogConfig.data = {
            title: 'Delete category',
            message: 'Are you sure you want to delete this category?'
        };

        const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(result => {
            if (result === "yes") {
                this.feedbackService.deleteCategory(category.id).subscribe(_ => {
                    this.initFaculties();

                    console.log("Deleted category");

                    this.snackBar.open('Successful category deletion', undefined, {
                        duration: 3000
                    });
                });
            }
        });
    }

    openUniversityYearDeleteDialog(universityYear: UniversityYear): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;

        dialogConfig.data = {
            title: 'Delete university year',
            message: 'Are you sure you want to delete this university year?'
        };

        const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(result => {
            if (result === "yes") {
                this.coreService.delete_university_year(universityYear.id).subscribe(_ => {
                    this.initUniversityYears();

                    console.log("Deleted university year");

                    this.snackBar.open('Successful university year deletion', undefined, {
                        duration: 3000
                    });
                });
            }
        });
    }

    openUniversityClassDeleteDialog(universityClass: UniversityClass): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;

        dialogConfig.data = {
            title: 'Delete university class',
            message: 'Are you sure you want to delete this university class?'
        };

        const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(result => {
            if (result === "yes") {
                this.coreService.delete_university_class(universityClass.id).subscribe(_ => {
                    this.initUniversityClasses();

                    console.log("Deleted university class");

                    this.snackBar.open('Successful university class deletion', undefined, {
                        duration: 3000
                    });
                });
            }
        });
    }

    openTeacherDeleteDialog(teacher: Teacher): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;

        dialogConfig.data = {
            title: 'Delete teacher',
            message: 'Are you sure you want to delete this teacher?'
        };

        const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(result => {
            if (result === "yes") {
                this.coreService.delete_teacher(teacher.id).subscribe(_ => {
                    this.initTeachers();

                    console.log("Deleted teacher");

                    this.snackBar.open('Successful teacher deletion', undefined, {
                        duration: 3000
                    });
                });
            }
        });
    }

    filterFaculties(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.faculties.filter = filterValue.trim().toLowerCase();
    }

    filterUniversityYears(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.universityYears.filter = filterValue.trim().toLowerCase();
    }

    filterTeachers(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.teachers.filter = filterValue.trim().toLowerCase();
    }

    filterUniversityClasses(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.universityClasses.filter = filterValue.trim().toLowerCase();
    }
}

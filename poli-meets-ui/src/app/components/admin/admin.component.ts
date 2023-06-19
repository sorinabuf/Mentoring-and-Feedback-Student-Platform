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
    faculties: MatTableDataSource<Faculty>;
    universityYears: MatTableDataSource<UniversityYear>;
    universityClasses: MatTableDataSource<UniversityClass>;
    teachers: MatTableDataSource<Teacher>;

    allFaculties: Faculty[];

    ngAfterViewInit() {
        this.faculties.paginator = this.paginator.toArray()[0];
        this.faculties.sort = this.sort.toArray()[0];

        this.universityYears.paginator = this.paginator.toArray()[1];
        this.universityYears.sort = this.sort.toArray()[1];

        this.universityClasses.paginator = this.paginator.toArray()[2];
        this.universityClasses.sort = this.sort.toArray()[2];

        this.teachers.paginator = this.paginator.toArray()[3];
        this.teachers.sort = this.sort.toArray()[3];
    }

    constructor(private coreService: CoreService, private dialog: MatDialog, private snackBar: MatSnackBar) {
        this.faculties = new MatTableDataSource();
        this.universityYears = new MatTableDataSource();
        this.universityClasses = new MatTableDataSource();
        this.teachers = new MatTableDataSource();
        this.selectedTabIndex = 1;
        this.allFaculties = [];
    }

    ngOnInit() {
        this.initFaculties();
        this.initUniversityYears();
        this.initUniversityClasses();
        this.initTeachers();
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
        });
    }

    initUniversityYears(): void {
        this.coreService.get_all_university_years().subscribe(universityYears => {
            this.universityYears.data = universityYears.reverse().map(element => { return { ...element, facultyName: this.getFacultyNameDomain(element), yearValue: this.getYear(element.year) } });
        });
    }

    initUniversityClasses(): void {
        this.coreService.get_all_university_classes().subscribe(universityClasses => {
            this.universityClasses.data = universityClasses;
        });
    }

    initTeachers(): void {
        this.coreService.get_all_teachers().subscribe(teachers => {
            this.teachers.data = teachers;
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
                if (faculty) {
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
                if (universityYear) {
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

    filterFaculties(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.faculties.filter = filterValue.trim().toLowerCase();
    }

    filterUniversityYears(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.universityYears.filter = filterValue.trim().toLowerCase();
    }

    filterUniversityClasses(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.universityClasses.filter = filterValue.trim().toLowerCase();
    }
}

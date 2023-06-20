import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from "@angular/material/dialog";
import { MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MtxDatetimepickerModule } from '@ng-matero/extensions/datetimepicker';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { NavbarComponent } from './components/nav/navbar/navbar.component';
import { ButtonMenuComponent } from './components/nav/button-menu/button-menu.component';
import { HomeComponent } from './components/home/home.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ConfirmationDialogComponent } from './components/dialog/confirmation-dialog/confirmation-dialog.component';
import { NoAuthGuardService } from './services/no-auth-guard.service';
import { AccountInformationDialogComponent } from './components/dialog/account-information-dialog/account-information-dialog.component';
import { NameInfoStepComponent } from './components/dialog/account-information-dialog/name-info-step/name-info-step.component';
import { FacultyInfoStepComponent } from './components/dialog/account-information-dialog/faculty-info-step/faculty-info-step.component';
import { WelcomeStepComponent } from './components/dialog/account-information-dialog/welcome-step/welcome-step.component';
import { GroupInfoStepComponent } from './components/dialog/account-information-dialog/group-info-step/group-info-step.component';
import { PhotoStepComponent } from './components/dialog/account-information-dialog/photo-step/photo-step.component';
import { AuthInterceptorService } from './services/auth-interceptor.service';
import { MyAccountComponent } from './components/my-account/my-account.component';
import { MentorDialogComponent } from './components/dialog/mentor-dialog/mentor-dialog.component';
import { MyFeedbackComponent } from './components/feedback/give-feedback/my-feedback/my-feedback.component';
import { SubjectsComponent } from './components/feedback/give-feedback/subjects/subjects.component';
import { FormComponent } from './components/feedback/give-feedback/form/form.component';
import { AllSubjectsComponent } from './components/feedback/view-feedback/all-subjects/all-subjects.component';
import { SubjectDetailsComponent } from './components/feedback/view-feedback/subject-details/subject-details.component';
import { PhotoDialogComponent } from './components/dialog/photo-dialog/photo-dialog.component';
import { MatRadioModule } from "@angular/material/radio";
import { FeedbackDialogComponent } from "./components/dialog/feedback-dialog/feedback-dialog.component";
import { MatButtonToggleModule } from "@angular/material/button-toggle";
import { UpcomingMeetingsComponent } from './components/mentorship/upcoming-meetings/upcoming-meetings.component';
import { MtxNativeDatetimeModule } from '@ng-matero/extensions/core';
import { FreeSlotComponent } from './components/dialog/free-slot/free-slot.component';
import { MentorsComponent } from './components/mentorship/mentors/mentors.component';
import { MentorFiltersDialogComponent } from './components/dialog/mentor-filters-dialog/mentor-filters-dialog.component';
import { BookMeetingDialogComponent } from './components/dialog/book-meeting-dialog/book-meeting-dialog.component';
import { PendingRequestsComponent } from './components/mentorship/pending-requests/pending-requests.component';
import { AdminComponent } from "./components/admin/admin.component";
import { MatTableModule } from "@angular/material/table";
import { ErrorInterceptorService } from './services/error-interceptor.service';
import { FacultyAdminDialogComponent } from './components/dialog/admin/faculty-admin-dialog/faculty-admin-dialog.component';
import { UniversityYearAdminDialogComponent } from './components/dialog/admin/university-year-admin-dialog/university-year-admin-dialog.component';
import {CategoryAdminDialogComponent} from "./components/dialog/admin/category-admin-dialog/category-admin-dialog.component";
import { TeacherAdminDialogComponent } from './components/dialog/admin/teacher-admin-dialog/teacher-admin-dialog.component';
import { UniversityClassAdminDialogComponent } from './components/dialog/admin/university-class-admin-dialog/university-class-admin-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ButtonMenuComponent,
    HomeComponent,
    FooterComponent,
    LoginComponent,
    RegisterComponent,
    PageNotFoundComponent,
    ConfirmationDialogComponent,
    AccountInformationDialogComponent,
    NameInfoStepComponent,
    FacultyInfoStepComponent,
    WelcomeStepComponent,
    GroupInfoStepComponent,
    PhotoStepComponent,
    MyAccountComponent,
    MentorDialogComponent,
    MyFeedbackComponent,
    SubjectsComponent,
    FormComponent,
    AllSubjectsComponent,
    SubjectDetailsComponent,
    PhotoDialogComponent,
    FeedbackDialogComponent,
    UpcomingMeetingsComponent,
    FreeSlotComponent,
    MentorsComponent,
    MentorFiltersDialogComponent,
    BookMeetingDialogComponent,
    PendingRequestsComponent,
    SubjectDetailsComponent,
    AdminComponent,
    FacultyAdminDialogComponent,
    UniversityYearAdminDialogComponent,
    CategoryAdminDialogComponent,
    TeacherAdminDialogComponent,
    UniversityClassAdminDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatMenuModule,
    MatButtonModule,
    MatToolbarModule,
    MatDividerModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatCheckboxModule,
    HttpClientModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule,
    MatSelectModule,
    MatTabsModule,
    MatChipsModule,
    MatProgressBarModule,
    MatRadioModule,
    MatButtonToggleModule,
    MtxDatetimepickerModule,
    MtxNativeDatetimeModule,
    MatRadioModule,
    MatGridListModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptorService, multi: true },
    NoAuthGuardService
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    ConfirmationDialogComponent,
    AccountInformationDialogComponent,
    MentorDialogComponent,
    PhotoDialogComponent,
    FreeSlotComponent,
    MentorFiltersDialogComponent,
    BookMeetingDialogComponent,
    FacultyAdminDialogComponent,
    UniversityYearAdminDialogComponent,
    TeacherAdminDialogComponent,
    UniversityClassAdminDialogComponent
  ]
})
export class AppModule {
}

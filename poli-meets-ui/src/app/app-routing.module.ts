import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { AuthGuardService } from './services/auth-guard.service';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { NoAuthGuardService } from './services/no-auth-guard.service';
import { MyAccountComponent } from './components/my-account/my-account.component';
import { MyFeedbackComponent } from './components/feedback/give-feedback/my-feedback/my-feedback.component';
import { UserResolver } from './resolvers/user.resolver';
import { NavbarComponent } from './components/nav/navbar/navbar.component';
import { SubjectsComponent } from './components/feedback/give-feedback/subjects/subjects.component';
import { FormComponent } from './components/feedback/give-feedback/form/form.component';
import { AllSubjectsComponent } from './components/feedback/view-feedback/all-subjects/all-subjects.component';
import { SubjectDetailsComponent } from './components/feedback/view-feedback/subject-details/subject-details.component';

export const routes: Routes = [
  { path: '', component: LoginComponent, canActivate: [NoAuthGuardService] },
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuardService] },
  { path: 'register', component: RegisterComponent, canActivate: [NoAuthGuardService] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService] },
  {
    path: 'my-account', component: MyAccountComponent, canActivate: [AuthGuardService], resolve: {
      student: UserResolver
    },
  },
  { path: 'feedback', redirectTo: 'feedback/me', pathMatch: 'full' },
  {
    path: 'feedback/me', component: MyFeedbackComponent,
    resolve: {
      student: UserResolver
    },
    canActivate: [AuthGuardService]
  },
  {
    path: 'feedback/me/subjects', component: SubjectsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'feedback/me/subjects/:id/form', component: FormComponent,
    canActivate: [AuthGuardService]
    // TODO: add user guard so it cannot be accessed manually
  },
  {
    path: 'feedback/subjects', component: AllSubjectsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'feedback/subjects/:id/details', component: SubjectDetailsComponent,
    canActivate: [AuthGuardService]
  },
  { path: 'not-found', component: PageNotFoundComponent },
  { path: '**', redirectTo: 'not-found' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherAdminDialogComponent } from './teacher-admin-dialog.component';

describe('TeacherAdminDialogComponent', () => {
  let component: TeacherAdminDialogComponent;
  let fixture: ComponentFixture<TeacherAdminDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeacherAdminDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeacherAdminDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

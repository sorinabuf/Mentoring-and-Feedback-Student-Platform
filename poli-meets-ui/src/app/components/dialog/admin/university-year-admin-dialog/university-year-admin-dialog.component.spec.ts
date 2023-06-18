import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UniversityYearAdminDialogComponent } from './university-year-admin-dialog.component';

describe('UniversityYearAdminDialogComponent', () => {
  let component: UniversityYearAdminDialogComponent;
  let fixture: ComponentFixture<UniversityYearAdminDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UniversityYearAdminDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UniversityYearAdminDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

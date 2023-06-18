import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacultyAdminDialogComponent } from './faculty-admin-dialog.component';

describe('FacultyAdminDialogComponent', () => {
  let component: FacultyAdminDialogComponent;
  let fixture: ComponentFixture<FacultyAdminDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FacultyAdminDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FacultyAdminDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

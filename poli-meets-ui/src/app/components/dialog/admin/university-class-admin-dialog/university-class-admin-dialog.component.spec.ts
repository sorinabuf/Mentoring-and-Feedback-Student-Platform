import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UniversityClassAdminDialogComponent } from './university-class-admin-dialog.component';

describe('UniversityClassAdminDialogComponent', () => {
  let component: UniversityClassAdminDialogComponent;
  let fixture: ComponentFixture<UniversityClassAdminDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UniversityClassAdminDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UniversityClassAdminDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

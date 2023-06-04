import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MentorFiltersDialogComponent } from './mentor-filters-dialog.component';

describe('MentorFiltersDialogComponent', () => {
  let component: MentorFiltersDialogComponent;
  let fixture: ComponentFixture<MentorFiltersDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MentorFiltersDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MentorFiltersDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

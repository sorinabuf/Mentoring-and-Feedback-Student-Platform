import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacultyInfoStepComponent } from './faculty-info-step.component';

describe('FacultyInfoStepComponent', () => {
  let component: FacultyInfoStepComponent;
  let fixture: ComponentFixture<FacultyInfoStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FacultyInfoStepComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FacultyInfoStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

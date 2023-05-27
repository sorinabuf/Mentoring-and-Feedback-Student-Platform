import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoStepComponent } from './photo-step.component';

describe('PhotoStepComponent', () => {
  let component: PhotoStepComponent;
  let fixture: ComponentFixture<PhotoStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PhotoStepComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PhotoStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

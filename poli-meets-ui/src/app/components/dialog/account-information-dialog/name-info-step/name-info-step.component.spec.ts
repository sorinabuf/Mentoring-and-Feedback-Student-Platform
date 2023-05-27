import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NameInfoStepComponent } from './name-info-step.component';

describe('NameInfoStepComponent', () => {
  let component: NameInfoStepComponent;
  let fixture: ComponentFixture<NameInfoStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NameInfoStepComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NameInfoStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

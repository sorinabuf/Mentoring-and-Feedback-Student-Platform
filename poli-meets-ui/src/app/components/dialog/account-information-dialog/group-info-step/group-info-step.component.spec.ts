import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupInfoStepComponent } from './group-info-step.component';

describe('GroupInfoStepComponent', () => {
  let component: GroupInfoStepComponent;
  let fixture: ComponentFixture<GroupInfoStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupInfoStepComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GroupInfoStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

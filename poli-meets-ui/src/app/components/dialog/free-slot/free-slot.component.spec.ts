import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FreeSlotComponent } from './free-slot.component';

describe('FreeSlotComponent', () => {
  let component: FreeSlotComponent;
  let fixture: ComponentFixture<FreeSlotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FreeSlotComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FreeSlotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookMeetingDialogComponent } from './book-meeting-dialog.component';

describe('BookMeetingDialogComponent', () => {
  let component: BookMeetingDialogComponent;
  let fixture: ComponentFixture<BookMeetingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookMeetingDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookMeetingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

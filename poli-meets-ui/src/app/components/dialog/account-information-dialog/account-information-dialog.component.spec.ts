import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountInformationDialogComponent } from './account-information-dialog.component';

describe('AccountInformationDialogComponent', () => {
  let component: AccountInformationDialogComponent;
  let fixture: ComponentFixture<AccountInformationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountInformationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountInformationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

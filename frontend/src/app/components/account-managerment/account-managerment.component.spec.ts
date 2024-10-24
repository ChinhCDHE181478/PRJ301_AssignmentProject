import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountManagermentComponent } from './account-managerment.component';

describe('AccountManagermentComponent', () => {
  let component: AccountManagermentComponent;
  let fixture: ComponentFixture<AccountManagermentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountManagermentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountManagermentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

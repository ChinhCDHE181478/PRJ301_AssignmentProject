import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendentComponent } from './attendent.component';

describe('AttendentComponent', () => {
  let component: AttendentComponent;
  let fixture: ComponentFixture<AttendentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AttendentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AttendentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

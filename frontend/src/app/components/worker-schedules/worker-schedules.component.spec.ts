import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkerSchedulesComponent } from './worker-schedules.component';

describe('WorkerSchedulesComponent', () => {
  let component: WorkerSchedulesComponent;
  let fixture: ComponentFixture<WorkerSchedulesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkerSchedulesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WorkerSchedulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

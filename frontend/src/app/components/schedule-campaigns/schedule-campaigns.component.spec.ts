import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleCampaignsComponent } from './schedule-campaigns.component';

describe('ScheduleCampaignsComponent', () => {
  let component: ScheduleCampaignsComponent;
  let fixture: ComponentFixture<ScheduleCampaignsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScheduleCampaignsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleCampaignsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

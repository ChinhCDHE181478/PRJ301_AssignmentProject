import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanCampaignsComponent } from './plan-campaigns.component';

describe('PlanCampaignsComponent', () => {
  let component: PlanCampaignsComponent;
  let fixture: ComponentFixture<PlanCampaignsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanCampaignsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlanCampaignsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

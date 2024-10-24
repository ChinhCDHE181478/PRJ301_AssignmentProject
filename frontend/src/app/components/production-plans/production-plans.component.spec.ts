import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductionPlansComponent } from './production-plans.component';

describe('ProductionPlansComponent', () => {
  let component: ProductionPlansComponent;
  let fixture: ComponentFixture<ProductionPlansComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductionPlansComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductionPlansComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

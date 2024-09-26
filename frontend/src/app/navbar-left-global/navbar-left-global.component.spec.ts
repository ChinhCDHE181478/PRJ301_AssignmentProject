import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarLeftGlobalComponent } from './navbar-left-global.component';

describe('NavbarLeftGlobalComponent', () => {
  let component: NavbarLeftGlobalComponent;
  let fixture: ComponentFixture<NavbarLeftGlobalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarLeftGlobalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarLeftGlobalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

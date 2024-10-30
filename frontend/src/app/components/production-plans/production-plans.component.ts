import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Plan } from '../../dto/plan';
import { Department } from '../../dto/department';
import { Router, RouterOutlet } from '@angular/router';
import { DepartmentService } from '../../service/department.service';
import { PlanService } from '../../service/plan.service';
import { MessageResponse } from '../../dto/messageResponse';
import { permission } from '../../permission/permission';
import { AuthStorageService } from '../../service/authStorage.service';

@Component({
  selector: 'app-production-plans',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderComponent,
    FormsModule,
    CommonModule,
    RouterOutlet
  ],
  templateUrl: './production-plans.component.html',
  styleUrl: './production-plans.component.scss',
})
export class ProductionPlansComponent implements OnInit {
  public permission = permission;

  role: string | null = null;

  departments: Department[] = [];

  productionPlans: Plan[] = [];

  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false;
  isEditModalOpen: boolean = false;

  newPlan: Plan = {
    planId: null,
    startDate: null,
    endDate: null,
    department: null,
  };

  searchCriteria: Plan = {
    planId: null,
    startDate: null,
    endDate: null,
    department: null,
  };

  selectedPlan: Plan = {
    planId: null,
    startDate: null,
    endDate: null,
    department: null,
  };

  titleEdit: string = '';

  idDelete: number | null = null;
  titleModalDelete: string = '';
  isVisible: boolean = false;
  showError: boolean = false;

  errors: {
    startDate: String | null;
    endDate: string | null;
    department: string | null;
  } = {
    startDate: null,
    endDate: null,
    department: null,
  };

  constructor(
    private departmentService: DepartmentService,
    private router: Router,
    private planService: PlanService,
    private authService: AuthStorageService
  ) {}

  ngOnInit(): void {
    this.role = this.authService.getPermission();

    this.allDept();

    this.all();
  }

  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  addProductionPlan() {
    if (!this.validatePlan(this.newPlan)) {
      return;
    }
    this.create();
    this.toggleAddModal();
  }

  resetPlan(plan: Plan) {
    plan.planId = null;
    plan.startDate = null;
    plan.endDate = null;
    plan.department = null;
  }

  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  performSearch() {
    this.search();
    this.toggleSearchModal();
  }

  openEditModal(plan: Plan) {
    this.titleEdit = 'Edit plan ' + plan.planId;
    this.selectedPlan = { ...plan };
    this.isEditModalOpen = true;
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.resetErrors();
  }

  saveEdit() {
    if (!this.validatePlan(this.selectedPlan)) {
      return;
    }
    this.update();
    this.closeEditModal();
  }

  viewCampaign(plan: Plan) {
    sessionStorage.setItem('plan', JSON.stringify(plan));
    this.router.navigateByUrl('campaign');
  }

  resetErrors() {
    this.errors = {
      startDate: null,
      endDate: null,
      department: null,
    };
  }

  validatePlan(plan: Plan): boolean {
    let isValid = true;

    if (!plan.startDate) {
      this.errors.startDate = 'Start date is required';
      isValid = false;
    } else {
      this.errors.startDate = null;
    }

    if (!plan.endDate) {
      this.errors.endDate = 'End date is required';
      isValid = false;
    } else if (plan.startDate && plan.endDate < plan.startDate) {
      this.errors.endDate = 'End date must equal or after the start date';
      isValid = false;
    } else {
      this.errors.endDate = null;
    }

    if (!plan.department?.departmentId) {
      this.errors.department = 'Department is required';
      isValid = false;
    } else {
      this.errors.department = null;
    }

    return isValid;
  }

  openModal(plan: Plan): void {
    this.titleModalDelete = 'Do you want to delete plan ' + plan.planId;
    this.idDelete = plan.planId;
    this.isVisible = true;
  }

  closeModal(): void {
    this.titleModalDelete = '';
    this.isVisible = false;
  }

  confirmDelete() {
    if (this.idDelete) {
      this.delete(this.idDelete);
    }
    this.closeModal();
  }

  closeError(): void {
    this.showError = false;
  }

  allDept() {
    this.departmentService.all().subscribe({
      next: (resp: Department[]) => {
        this.departments = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  all() {
    this.planService.all().subscribe({
      next: (resp: Plan[]) => {
        this.productionPlans = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  search() {
    this.planService.search(this.searchCriteria).subscribe({
      next: (resp: Plan[]) => {
        this.productionPlans = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  create() {
    this.planService.create(this.newPlan).subscribe({
      next: (resp: Plan) => {
        console.log("Created: " + resp);
        this.all();
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  update() {
    this.planService.update(this.selectedPlan).subscribe({
      next: (resp: Plan) => {
        console.log("Updated: " + resp);
        this.all();
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  delete(id: number) {
    this.planService.delete(id).subscribe({
      next: (resp: MessageResponse) => {
        this.all();
        alert(resp.message);
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }
}

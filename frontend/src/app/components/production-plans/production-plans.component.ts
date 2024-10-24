import { Component, EventEmitter, Output } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { HeaderComponent } from "../header/header.component";
import { FormsModule } from "@angular/forms"
import { CommonModule } from '@angular/common';
import { Plan } from '../../common/plan';
import { Department } from '../../common/department';

@Component({
  selector: 'app-production-plans',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, FormsModule, CommonModule],
  templateUrl: './production-plans.component.html',
  styleUrl: './production-plans.component.scss'
})
export class ProductionPlansComponent {

  // === Predefined Data ===
  departments: Department[] = [
    { departmentId: 1, departmentName: 'Manufacturing' , departmentType: "WS"},
    { departmentId: 2, departmentName: 'Logistics', departmentType: "WS" },
    { departmentId: 3, departmentName: 'Quality Control', departmentType: "WS" },
    { departmentId: 4, departmentName: 'R&D', departmentType: "WS" }
  ];

  productionPlans: Plan[] = [
    { planId: 1, startDate: new Date('2024-01-01'), endDate: new Date('2024-03-01'), department: this.departments[0] },
    { planId: 2, startDate: new Date('2024-02-15'), endDate: new Date('2024-05-15'), department: this.departments[2] }
  ];

  // === Modal Control Variables ===
  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false;
  isEditModalOpen: boolean = false;

  // === Models ===

  // Model: New Plan
  newPlan: Plan = { 
    planId: null, 
    startDate: null, 
    endDate: null, 
    department: null
  };

  // Model: Search Criteria
  searchCriteria: Plan = { 
    planId: null, 
    startDate: null, 
    endDate: null, 
    department: null
  };

  // Model: Selected Plan
  selectedPlan: Plan = { 
    planId: null, 
    startDate: null, 
    endDate: null, 
    department: null
  };

  // === Methods Related to New Plan ===

  toggleAddModal(){
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  // Add New Production Plan
  addProductionPlan() {
    if(!this.validatePlan(this.newPlan)){
        return;
    }
    console.log('Adding new production plan:', this.newPlan);
    // Add plan logic
    this.toggleAddModal();
    this.resetPlan(this.newPlan);
  }

  // Reset New Plan
  resetPlan(plan: Plan) { 
    plan.planId = null;
    plan.startDate = null;
    plan.endDate = null;
    plan.department = null;
  }

  // === Methods Related to Search Criteria ===

  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  // Perform Search
  performSearch() {
    console.log('Search Criteria:', this.searchCriteria);
    // Search logic
    this.toggleSearchModal();
  }

  // === Methods Related to Selected Plan ===

  titleEdit : string = "";

  // Open Edit Modal
  openEditModal(plan: Plan) {
    this.titleEdit = "Edit plan " + plan.planId;
    this.selectedPlan = { ...plan };
    this.isEditModalOpen = true;
  }

  // Close Edit Modal
  closeEditModal() {
    this.isEditModalOpen = false;
    this.resetErrors();
  }

  // Save Edited Plan
  saveEdit() {
    if (this.selectedPlan) {
      if(!this.validatePlan(this.selectedPlan)){
        return;
      }
      console.log('Updated Plan:', this.selectedPlan);
      // Update logic
    } 
    this.closeEditModal();
  }


  // === General Methods ===

  // View All Plans
  viewAllPlans() {
    console.log('View all plans triggered.');
        // Logic to fetch and display all production plans
  }

  // View Campaign
  viewCampaign(plan: Plan) {
    sessionStorage.setItem('plan', JSON.stringify(plan));
    console.log('Redirecting to campaign view:', plan);
    // Logic to navigate to the campaign page
  }

  delete(plan: Plan){
    //delete if no child
  }

  errors: {
    startDate: String | null,
    endDate: string | null,
    department: string | null
  } = {
    startDate: null,
    endDate: null,
    department: null
  }

  resetErrors(){
    this.errors = {
      startDate: null,
      endDate: null,
      department: null
    }
  }

  validatePlan(plan : Plan): boolean{
    let isValid = true;
    
    if(!plan.startDate){
      this.errors.startDate = "Start date is required";
      isValid = false;
    } else {
      this.errors.startDate = null;
    }

    if(!plan.endDate){
      this.errors.endDate = "End date is required";
      isValid = false;
    } else if(plan.startDate && (plan.endDate < plan.startDate)) {
      this.errors.endDate = "End date must equal or after the start date";
      isValid = false;
    } else {
      this.errors.endDate = null;
    }

    if(plan.department){
      this.errors.department = "Department is required"
      isValid = false;
    } else {
      this.errors.department = null;
    }

    return isValid;
  }




  idDelete : number | null = null;
  titleModalDelete: string = '';
  isVisible: boolean = false;
  showError: boolean = false;

  @Output() deleteConfirmed: EventEmitter<void> = new EventEmitter<void>();

  openModal(plan : Plan): void {
    this.titleModalDelete = "Do you want to delete plan " + plan.planId;
    this.idDelete = plan.planId;
    this.isVisible = true;
  }

  closeModal(): void {
    this.titleModalDelete = '';
    this.isVisible = false;
  }

  confirmDelete(): void {
    // Condition check here (example condition: always false for demonstration)
    const canDelete = false; // Replace with actual condition check

    if (canDelete) {
      this.deleteConfirmed.emit();
      this.closeModal();
    } else {
      this.closeModal();
      this.showError = true;
    }
  }

  closeError(): void {
    this.showError = false;
  }

}

import { Component, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Product } from '../../common/product';
import { Campaign } from '../../common/campaign';
import { Router } from '@angular/router';
import { Plan } from '../../common/plan';

@Component({
  selector: 'app-plan-campaigns',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CommonModule, FormsModule],
  templateUrl: './plan-campaigns.component.html',
  styleUrls: ['./plan-campaigns.component.scss'],
})
export class PlanCampaignsComponent {
  plan: Plan | null = null;

  constructor(private router: Router) {
    const planSession = sessionStorage.getItem('plan');
    if (planSession) {
      this.plan = JSON.parse(planSession);
      //get list
    } else {
      //get all list
    }
  }

  viewSchedule(campaign: Campaign) {
    sessionStorage.setItem('campaign', JSON.stringify(campaign));
    sessionStorage.removeItem('plan');
    //route schedule
  }

  viewAllCampaigns() {
    this.plan = null;
    sessionStorage.removeItem('plan');
    //set all
  }

  // === Biến Điều Khiển Modal ===
  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false;
  isEditModalOpen: boolean = false;
  isPlanSessionAvailable: boolean = false;

  // === Dữ Liệu Mẫu ===
  products: Product[] = [
    { productId: 1, productName: 'Product A' },
    { productId: 2, productName: 'Product B' },
    { productId: 3, productName: 'Product C' },
  ];

  campaigns: Campaign[] = [
    {
      campaignId: 1,
      planId: 101,
      product: this.products[0],
      quantity: 50,
      unitEffortDays: 5,
    },
    {
      campaignId: 2,
      planId: 102,
      product: this.products[2],
      quantity: 75,
      unitEffortDays: 3,
    },
  ];

  // === Model ===
  newCampaign: Campaign = {
    campaignId: null,
    planId: null,
    product: null,
    quantity: null,
    unitEffortDays: null,
  };

  searchCriteria: Campaign = {
    campaignId: null,
    planId: null,
    product: null,
    quantity: null,
    unitEffortDays: null,
  };

  selectedCampaign: Campaign = {
    campaignId: null,
    planId: null,
    product: null,
    quantity: null,
    unitEffortDays: null,
  };

  // === Phương Thức Liên Quan Đến New Campaign ===
  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  addCampaign() {
    if (!this.validateCampaign(this.newCampaign)) {
      return;
    }
    console.log(this.newCampaign);

    this.toggleAddModal();
    this.resetCampaign(this.newCampaign);
  }

  resetCampaign(cam: Campaign) {
    cam.campaignId = null;
    cam.planId = null;
    cam.product = null;
    cam.quantity = null;
    cam.unitEffortDays = null;
  }

  // === Phương Thức Liên Quan Đến Selected Campaign ===
  titleEdit: string = "";

  openEditModal(campaign: Campaign) {
    this.selectedCampaign = { ...campaign };
    this.titleEdit = "Edit campaign " + campaign.campaignId;
    this.isEditModalOpen = true;
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.resetErrors();
  }

  saveEdit() {
    if(!this.validateCampaign(this.selectedCampaign)){
      return;
    }
    console.log('Updated Campaign:', this.selectedCampaign);
    this.closeEditModal();
  }

  // === Phương Thức Liên Quan Đến Search Criteria ===
  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  performSearch() {
    console.log('Search Criteria:', this.searchCriteria);
    this.toggleSearchModal();
  }

  errors: {
    planId: String | null;
    product: String | null;
    quantity: String | null;
    unitEffort: String | null;
  } = {
    planId: null,
    product: null,
    quantity: null,
    unitEffort: null,
  };

  resetErrors(){
    this.errors = {
      planId : null,
      product : null,
      quantity : null,
      unitEffort : null
    }
  }

  validateCampaign(campaign: Campaign): boolean {
    let isValid = true;

    if (!campaign.planId || !Number.isInteger(campaign.planId) || campaign.planId <= 0) {
      this.errors.planId = 'Plan ID is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.planId = null;
    }

    if (campaign.product) {
      this.errors.product = null;
    } else {
      this.errors.product = 'Product is required';
      isValid = false;
    }

    if (!campaign.quantity || !Number.isInteger(campaign.quantity) || campaign.quantity <= 0) {
      this.errors.quantity = 'Quantity is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.quantity = null;
    }

    if (!campaign.unitEffortDays || !Number.isInteger(campaign.unitEffortDays) || campaign.unitEffortDays <= 0) {
      this.errors.unitEffort = 'Unit effort is requied and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.unitEffort = null;
    }

    return isValid;
  }


  idDelete : number | null = null;
  titleModalDelete: string = '';
  isVisible: boolean = false;
  showError: boolean = false;

  @Output() deleteConfirmed: EventEmitter<void> = new EventEmitter<void>();

  openModal(campaign : Campaign): void {
    this.titleModalDelete = "Do you want to delete campaign " + campaign.campaignId;
    this.idDelete = campaign.campaignId;
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

import {
  Component,
  ViewChild,
  ElementRef,
  Output,
  EventEmitter,
  OnInit,
} from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Product } from '../../dto/product';
import { Campaign } from '../../dto/campaign';
import { Router, RouterOutlet } from '@angular/router';
import { Plan } from '../../dto/plan';
import { ProductService } from '../../service/product.service';
import { CampaignService } from '../../service/campaign.service';
import { MessageResponse } from '../../dto/messageResponse';
import { permission } from '../../permission/permission';
import { AuthStorageService } from '../../service/authStorage.service';

@Component({
  selector: 'app-plan-campaigns',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    CommonModule,
    FormsModule,
    RouterOutlet,
  ],
  templateUrl: './plan-campaigns.component.html',
  styleUrls: ['./plan-campaigns.component.scss'],
})
export class PlanCampaignsComponent implements OnInit {
  public permission = permission;

  role: string | null = null;

  plan: Plan | null = null;

  products: Product[] = [];

  campaigns: Campaign[] = [];

  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false;
  isEditModalOpen: boolean = false;
  isPlanSessionAvailable: boolean = false;

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

  idDelete: number | null = null;
  titleModalDelete: string = '';
  isVisible: boolean = false;
  showError: boolean = false;

  titleEdit: string = '';

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

  constructor(
    private router: Router,
    private productService: ProductService,
    private campaignService: CampaignService,
    private authService: AuthStorageService
  ) {}

  ngOnInit(): void {
    this.role = this.authService.getPermission();

    this.allProduct();

    const planSession = sessionStorage.getItem('plan');
    if (planSession) {
      this.plan = JSON.parse(planSession);
      if (this.plan?.planId) {
        this.searchCriteria.planId = this.plan.planId;
        this.search();
      }
    } else {
      this.all();
    }
  }

  viewSchedule(campaign: Campaign) {
    sessionStorage.setItem('campaign', JSON.stringify(campaign));
    this.router.navigateByUrl('schedule');
  }

  viewAllCampaigns() {
    this.plan = null;
    sessionStorage.removeItem('plan');
    this.all();
  }

  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  addCampaign() {
    if (!this.validateCampaign(this.newCampaign)) {
      return;
    }
    this.create();
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

  openEditModal(campaign: Campaign) {
    this.selectedCampaign = { ...campaign };
    this.titleEdit = 'Edit campaign ' + campaign.campaignId;
    this.isEditModalOpen = true;
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.resetErrors();
  }

  saveEdit() {
    if (!this.validateCampaign(this.selectedCampaign)) {
      return;
    }
    this.update();
    this.closeEditModal();
  }

  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  performSearch() {
    this.search();
    this.toggleSearchModal();
  }

  resetErrors() {
    this.errors = {
      planId: null,
      product: null,
      quantity: null,
      unitEffort: null,
    };
  }

  validateCampaign(campaign: Campaign): boolean {
    let isValid = true;

    if (
      !campaign.planId ||
      !Number.isInteger(campaign.planId) ||
      campaign.planId <= 0
    ) {
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

    if (
      !campaign.quantity ||
      !Number.isInteger(campaign.quantity) ||
      campaign.quantity <= 0
    ) {
      this.errors.quantity =
        'Quantity is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.quantity = null;
    }

    if (
      !campaign.unitEffortDays ||
      !Number.isInteger(campaign.unitEffortDays) ||
      campaign.unitEffortDays <= 0
    ) {
      this.errors.unitEffort =
        'Unit effort is requied and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.unitEffort = null;
    }

    return isValid;
  }

  openModal(campaign: Campaign): void {
    this.titleModalDelete =
      'Do you want to delete campaign ' + campaign.campaignId;
    this.idDelete = campaign.campaignId;
    this.isVisible = true;
  }

  closeModal(): void {
    this.titleModalDelete = '';
    this.isVisible = false;
  }

  confirmDelete(): void {
    if (this.idDelete) {
      this.delete(this.idDelete);
    }
    this.closeModal();
  }

  allProduct() {
    this.productService.all().subscribe({
      next: (resp: Product[]) => {
        this.products = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  all() {
    this.campaignService.all().subscribe({
      next: (resp: Campaign[]) => {
        this.campaigns = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  search() {
    this.campaignService.search(this.searchCriteria).subscribe({
      next: (resp: Campaign[]) => {
        this.campaigns = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  create() {
    this.campaignService.create(this.newCampaign).subscribe({
      next: (resp: Campaign) => {
        console.log('Created: ' + resp);
        this.all();
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }

  update() {
    this.campaignService.update(this.selectedCampaign).subscribe({
      next: (resp: Campaign) => {
        console.log('Updated: ' + resp);
        this.all();
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }

  delete(id: number) {
    this.campaignService.delete(id).subscribe({
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

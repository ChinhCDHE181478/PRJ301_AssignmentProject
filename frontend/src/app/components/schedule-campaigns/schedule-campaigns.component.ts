import { Component, EventEmitter, Output } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Shift } from '../../common/shift';
import { Product } from '../../common/product';
import { Department } from '../../common/department';
import { Campaign } from '../../common/campaign';
import { Plan } from '../../common/plan';
import { Schedule } from '../../common/schedule';

@Component({
  selector: 'app-schedule-campaigns',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule, CommonModule],
  templateUrl: './schedule-campaigns.component.html',
  styleUrl: './schedule-campaigns.component.scss',
})
export class ScheduleCampaignsComponent {
  //fake data
  shifts: Shift[] = [
    {
      shiftId: 1,
      shiftName: 'Morning Shift',
      shirtStart: '08:00:00',
      shiftEnd: '12:00:00',
    },
    {
      shiftId: 2,
      shiftName: 'Afternoon Shift',
      shirtStart: '13:00:00',
      shiftEnd: '17:00:00',
    },
    {
      shiftId: 3,
      shiftName: 'Night Shift',
      shirtStart: '18:00:00',
      shiftEnd: '22:00:00',
    },
  ];

  mockProduct: Product = {
    productId: 1,
    productName: 'Sample Product',
  };

  // Dữ liệu giả cho Department
  mockDepartment: Department = {
    departmentId: 1,
    departmentName: 'Production',
    departmentType: 'WS',
  };

  // Dữ liệu giả cho Campaign
  mockCampaign: Campaign = {
    campaignId: 1,
    planId: 1,
    product: this.mockProduct,
    quantity: 500,
    unitEffortDays: 10,
  };

  // Mảng dữ liệu giả cho Schedule
  schedules: Schedule[] = [
    {
      scheduleId: 1,
      campaignId: this.mockCampaign.campaignId,
      date: new Date('2024-04-05'),
      quantity: 100,
      shift: this.shifts[0], // Morning Swift
    },
    {
      scheduleId: 2,
      campaignId: this.mockCampaign.campaignId,
      date: new Date('2024-04-06'),
      quantity: 150,
      shift: this.shifts[1], // Afternoon Shift
    },
    {
      scheduleId: 3,
      campaignId: this.mockCampaign.campaignId,
      date: new Date('2024-04-07'),
      quantity: 200,
      shift: this.shifts[2], // Night Shift
    },
  ];

  campaign: Campaign | null = null;

  constructor() {
    const campaignSession = sessionStorage.getItem('campaign');
    if (campaignSession) {
      this.campaign = JSON.parse(campaignSession);
      //get list
    } else {
      //get all
    }
  }

  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false; // <-- Initialize this
  isEditModalOpen: boolean = false;
  // Add criteria
  newSchedule: Schedule = {
    scheduleId: null,
    campaignId: null,
    date: null,
    quantity: null,
    shift: null,
  };

  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  addRecord() {
    if(!this.validateSchedule(this.newSchedule)){
      return;
    }
    //add record
    this.toggleAddModal(); // Close the modal after adding
    this.clearSchedule(this.newSchedule);
  }

  clearSchedule(schedule: Schedule) {
    (schedule.scheduleId = null),
      (schedule.campaignId = null),
      (schedule.date = null),
      (schedule.quantity = null),
      (schedule.shift = null);
  }

  // Search criteria
  searchCiteria: Schedule = {
    scheduleId: null,
    campaignId: null,
    date: null,
    quantity: null,
    shift: null,
  };

  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  performSearch() {
    // Filter logic here
    this.toggleSearchModal();
  }

  editSchedule: Schedule = {
    scheduleId: null,
    campaignId: null,
    date: null,
    quantity: null,
    shift: null,
  };
  titleEdit = '';

  openEditModal(schedule: Schedule) {
    this.editSchedule = { ...schedule };
    this.titleEdit = 'Edit Schedule ' + schedule.scheduleId;
    this.isEditModalOpen = true;
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.resetErrors();
  }

  saveEdit() {
    if (!this.validateSchedule(this.editSchedule)) {
      return;
    }
    
    this.closeEditModal();
  }

  viewWorker(schedule: Schedule) {
    sessionStorage.setItem('schedule', JSON.stringify(schedule));
    sessionStorage.removeItem('campaign');
    //route
  }

  viewAll() {
    if (!this.campaign) {
      return;
    }
    this.campaign = null;
    sessionStorage.removeItem('campaign');
    //set all
  }

  errors: {
    scheduleId: string | null;
    campaignId: string | null;
    date: string | null;
    quantity: string | null;
    shift: string | null;
  } = {
    scheduleId: null,
    campaignId: null,
    date: null,
    quantity: null,
    shift: null,
  };

  resetErrors(){
    this.errors = {
      scheduleId : null,
      campaignId : null,
      date: null,
      quantity: null,
      shift: null
    }
  }

  validateSchedule(schedule: Schedule) : boolean {
    let isValid = true;

    if (!schedule.scheduleId || !Number.isInteger(schedule.scheduleId) || schedule.scheduleId <= 0) {
      this.errors.scheduleId = 'Schedule ID is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.scheduleId = null;
    }

    if (!schedule.campaignId || !Number.isInteger(schedule.campaignId) || schedule.campaignId <= 0) {
      this.errors.campaignId = 'Campaign ID is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.campaignId = null;
    }

    if (!schedule.date) {
      this.errors.date = 'Date is required';
      isValid = false;
    } else {
      this.errors.date = null;
    }

    if (!schedule.shift) {
      this.errors.shift = 'Shift is required';
      isValid = false;
    } else {
      this.errors.shift = null;
    }

    if (!schedule.quantity || !Number.isInteger(schedule.quantity) || schedule.quantity <= 0) {
      this.errors.quantity = 'Quantity ID is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.quantity = null;
    }

    return isValid;
  }

  idDelete: number | null = null;
  titleModalDelete: string = '';
  isVisible: boolean = false;
  showError: boolean = false;

  @Output() deleteConfirmed: EventEmitter<void> = new EventEmitter<void>();

  openModal(schedule: Schedule): void {
    this.titleModalDelete =
      'Do you want to delete schedule ' + schedule.scheduleId;
    this.idDelete = schedule.scheduleId;
    this.isVisible = true;
  }

  closeModal(): void {
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

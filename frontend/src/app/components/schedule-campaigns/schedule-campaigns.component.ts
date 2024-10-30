import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Shift } from '../../dto/shift';
import { Campaign } from '../../dto/campaign';
import { Schedule } from '../../dto/schedule';
import { permission } from '../../permission/permission';
import { Router, RouterOutlet } from '@angular/router';
import { AuthStorageService } from '../../service/authStorage.service';
import { ShiftService } from '../../service/shift.service';
import { ScheduleService } from '../../service/schedule.service';
import { MessageResponse } from '../../dto/messageResponse';

@Component({
  selector: 'app-schedule-campaigns',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    FormsModule,
    CommonModule,
    RouterOutlet,
  ],
  templateUrl: './schedule-campaigns.component.html',
  styleUrl: './schedule-campaigns.component.scss',
})
export class ScheduleCampaignsComponent implements OnInit {
  public permission = permission;

  role: string | null = null;

  shifts: Shift[] = [];

  schedules: Schedule[] = [];

  campaign: Campaign | null = null;

  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false;
  isEditModalOpen: boolean = false;

  newSchedule: Schedule = {
    scheduleId: null,
    campaignId: null,
    date: null,
    quantity: null,
    shift: null,
  };

  searchCiteria: Schedule = {
    scheduleId: null,
    campaignId: null,
    date: null,
    quantity: null,
    shift: null,
  };

  editSchedule: Schedule = {
    scheduleId: null,
    campaignId: null,
    date: null,
    quantity: null,
    shift: null,
  };

  titleEdit = '';

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

  idDelete: number | null = null;
  titleModalDelete: string = '';
  isVisible: boolean = false;
  showError: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthStorageService,
    private shiftService: ShiftService,
    private scheduleService: ScheduleService
  ) {}

  ngOnInit(): void {
    this.role = this.authService.getPermission();

    this.allShift();

    const campaignSession = sessionStorage.getItem('campaign');
    if (campaignSession) {
      this.campaign = JSON.parse(campaignSession);
      if (this.campaign?.campaignId) {
        this.searchCiteria.campaignId = this.campaign.campaignId;
        this.search();
      }
    } else {
      this.all();
    }
  }

  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  addRecord() {
    if (!this.validateSchedule(this.newSchedule)) {
      return;
    }
    this.create();
    this.toggleAddModal();
    this.clearSchedule(this.newSchedule);
  }

  clearSchedule(schedule: Schedule) {
    (schedule.scheduleId = null),
      (schedule.campaignId = null),
      (schedule.date = null),
      (schedule.quantity = null),
      (schedule.shift = null);
  }

  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  performSearch() {
    this.search();
    this.toggleSearchModal();
  }

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
    this.update();
    this.closeEditModal();
  }

  viewWorker(schedule: Schedule) {
    sessionStorage.setItem('schedule', JSON.stringify(schedule));
    this.router.navigateByUrl('worker');
  }

  viewAll() {
    this.campaign = null;
    sessionStorage.removeItem('campaign');
    this.all();
  }

  resetErrors() {
    this.errors = {
      scheduleId: null,
      campaignId: null,
      date: null,
      quantity: null,
      shift: null,
    };
  }

  validateSchedule(schedule: Schedule): boolean {
    let isValid = true;

    if (
      !schedule.scheduleId ||
      !Number.isInteger(schedule.scheduleId) ||
      schedule.scheduleId <= 0
    ) {
      this.errors.scheduleId =
        'Schedule ID is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.scheduleId = null;
    }

    if (
      !schedule.campaignId ||
      !Number.isInteger(schedule.campaignId) ||
      schedule.campaignId <= 0
    ) {
      this.errors.campaignId =
        'Campaign ID is required and must be natural number > 0';
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

    if (
      !schedule.quantity ||
      !Number.isInteger(schedule.quantity) ||
      schedule.quantity <= 0
    ) {
      this.errors.quantity =
        'Quantity ID is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.quantity = null;
    }

    return isValid;
  }

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
    if (this.idDelete) {
      this.delete(this.idDelete);
    }
    this.all();
    this.closeModal();
  }

  allShift() {
    this.shiftService.all().subscribe({
      next: (resp: Shift[]) => {
        this.shifts = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  all() {
    this.scheduleService.all().subscribe({
      next: (resp: Schedule[]) => {
        this.schedules = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  search() {
    this.scheduleService.search(this.searchCiteria).subscribe({
      next: (resp: Schedule[]) => {
        this.schedules = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  create() {
    this.scheduleService.create(this.newSchedule).subscribe({
      next: (resp: Schedule) => {
        console.log('Created: ' + resp);
        this.all();
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }

  update() {
    this.scheduleService.update(this.editSchedule).subscribe({
      next: (resp: Schedule) => {
        console.log('Updated: ' + resp);
        this.all();
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }

  delete(id: number) {
    this.scheduleService.delete(id).subscribe({
      next: (resp: MessageResponse) => {
        alert(resp.message);
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Attendent } from '../../dto/attendent';
import { Worker } from '../../dto/worker';
import { Department } from '../../dto/department';
import { Employee } from '../../dto/employee';
import { Schedule } from '../../dto/schedule';
import { Router, RouterOutlet } from '@angular/router';
import { AttendentService } from '../../service/attendent.service';
import { MessageResponse } from '../../dto/messageResponse';

@Component({
  selector: 'app-worker-schedules',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    FormsModule,
    CommonModule,
    RouterOutlet,
  ],
  templateUrl: './worker-schedules.component.html',
  styleUrl: './worker-schedules.component.scss',
})
export class WorkerSchedulesComponent implements OnInit {
  schedule: Schedule | null = null;

  attendents: Attendent[] = [];

  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false;
  isEditModalOpen: boolean = false;

  newAttendent: Attendent = {
    attendentId: null,
    worker: {
      workerId: null,
      employee: {
        employeeId: null,
        employeeName: null,
        department: null,
      },
      quantity: null,
      scheduleId: null,
    },
    quantity: null,
    alpha: null,
  };

  searchCiteria: Attendent = {
    attendentId: null,
    worker: {
      workerId: null,
      employee: {
        employeeId: null,
        employeeName: null,
        department: null,
      },
      quantity: null,
      scheduleId: null,
    },
    quantity: null,
    alpha: null,
  };

  editAttendent: Attendent = {
    attendentId: null,
    worker: {
      workerId: null,
      employee: {
        employeeId: null,
        employeeName: null,
        department: null,
      },
      quantity: null,
      scheduleId: null,
    },
    quantity: null,
    alpha: null,
  };

  titleEdit: string = '';

  errors: {
    scheduleId: string | null;
    plannedQuantity: string | null;
    actualQuantity: string | null;
    employeeId: string | null;
    alpha: string | null;
  } = {
    actualQuantity: null,
    alpha: null,
    employeeId: null,
    plannedQuantity: null,
    scheduleId: null,
  };

  idDelete: number | null = null;
  titleModalDelete: string = '';
  isVisible: boolean = false;

  constructor(
    private attendentService: AttendentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const scheduleData = sessionStorage.getItem('schedule');
    if (scheduleData) {
      this.schedule = JSON.parse(scheduleData);
      if (this.schedule?.scheduleId) {
        this.searchCiteria.worker.scheduleId = this.schedule.scheduleId;
        this.search();
      }
    } else {
      this.all();
    }
  }

  viewEmployee(attendent: Attendent) {
    sessionStorage.setItem('attendent', JSON.stringify(attendent));
    this.router.navigateByUrl('employee');
  }

  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  addRecord() {
    if (!this.validateAttendent(this.newAttendent)) {
      return;
    }
    this.create();
    this.toggleAddModal();
    this.resetAttendent(this.newAttendent);
  }

  resetAttendent(attendent: Attendent) {
    attendent.attendentId = null;
    attendent.worker.workerId = null;
    attendent.worker.scheduleId = null;
    attendent.worker.quantity = null;
    attendent.worker.employee.employeeId = null;
    attendent.worker.employee.employeeName = null;
    attendent.worker.employee.department = null;
    attendent.quantity = null;
    attendent.alpha = null;
  }

  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  performSearch() {
    this.search();
    this.toggleSearchModal();
  }

  openEditModal(attendent: Attendent) {
    this.titleEdit = 'Edit worker schedule ' + attendent.worker.workerId;
    this.editAttendent = { ...attendent };
    this.isEditModalOpen = true;
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.resetErrors();
  }

  saveEdit() {
    if (!this.validateAttendent(this.editAttendent)) {
      return;
    }
    this.update();
    this.closeEditModal();
  }

  viewAll() {
    sessionStorage.removeItem('schedule');
    this.all();
  }

  resetErrors() {
    this.errors.actualQuantity = null;
    this.errors.alpha = null;
    this.errors.employeeId = null;
    this.errors.plannedQuantity = null;
    this.errors.scheduleId = null;
  }

  validateAttendent(attendent: Attendent): boolean {
    let isValid = true;

    if (
      !attendent.worker.scheduleId ||
      !Number.isInteger(attendent.worker.scheduleId) ||
      attendent.worker.scheduleId <= 0
    ) {
      this.errors.scheduleId =
        'Schedule ID is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.scheduleId = null;
    }

    if (
      !attendent.worker.quantity ||
      !Number.isInteger(attendent.worker.quantity) ||
      attendent.worker.quantity <= 0
    ) {
      this.errors.plannedQuantity =
        'Planned quantity is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.plannedQuantity = null;
    }

    if (
      !attendent.worker.employee.employeeId ||
      !Number.isInteger(attendent.worker.employee.employeeId) ||
      attendent.worker.employee.employeeId <= 0
    ) {
      this.errors.employeeId =
        'Employee ID is required and must be natural number > 0';
      isValid = false;
    } else {
      this.errors.employeeId = null;
    }

    if (
      attendent.alpha !== null &&
      attendent.alpha !== undefined &&
      attendent.alpha < 0
    ) {
      this.errors.alpha = 'Alpha must be a number >= 0 if provided';
      isValid = false;
    } else {
      this.errors.alpha = null;
    }

    if (
      attendent.quantity !== null &&
      attendent.quantity !== undefined &&
      (!Number.isInteger(attendent.quantity) || attendent.quantity < 0)
    ) {
      this.errors.actualQuantity =
        'Actual quantity must be a natural number >= 0 if provided';
      isValid = false;
    } else {
      this.errors.actualQuantity = null;
    }

    return isValid;
  }

  openModal(attendent: Attendent): void {
    this.titleModalDelete =
      'Do you want to delete worker schedule ' + attendent.worker.workerId;
    this.idDelete = attendent.worker.workerId;
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

  all() {
    this.attendentService.all().subscribe({
      next: (resp: Attendent[]) => {
        this.attendents = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  search() {
    this.attendentService.search(this.searchCiteria).subscribe({
      next: (resp: Attendent[]) => {
        this.attendents = resp;
      },
      error: (error: any) => {
        console.log(error.error);
      },
    });
  }

  create() {
    this.attendentService.create(this.newAttendent).subscribe({
      next: (resp: Attendent) => {
        console.log('Created: ' + resp);
        this.all();
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }

  update() {
    this.attendentService.update(this.editAttendent).subscribe({
      next: (resp: Attendent) => {
        console.log('Updated: ' + resp);
        this.all();
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }

  delete(id: number) {
    this.attendentService.delete(id).subscribe({
      next: (resp: MessageResponse) => {
        alert(resp.message);
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }
}

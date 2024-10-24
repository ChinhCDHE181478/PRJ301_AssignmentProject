import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Attendent } from '../../common/attendent';
import { Worker } from '../../common/worker';
import { Department } from '../../common/department';
import { Employee } from '../../common/employee';
import { Schedule } from '../../common/schedule';

@Component({
  selector: 'app-worker-schedules',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule, CommonModule],
  templateUrl: './worker-schedules.component.html',
  styleUrl: './worker-schedules.component.scss',
})
export class WorkerSchedulesComponent {

    schedule: Schedule | null = null;

    constructor(){
        const scheduleData = sessionStorage.getItem("schedule");
        if(scheduleData){
            this.schedule = JSON.parse(scheduleData);
            //get list
        } else {
            //get all
        }
    }

  // Tạo dữ liệu giả cho Department
  departmentData: Department = {
    departmentId: 1,
    departmentName: 'Production',
    departmentType: 'WS',
  };

  // Tạo dữ liệu giả cho Employee
  employee1: Employee = {
    employeeId: 101,
    employeeName: 'Nguyen Van A',
    department: this.departmentData,
  };

  employee2: Employee = {
    employeeId: 102,
    employeeName: 'Tran Thi B',
    department: this.departmentData,
  };

  // Tạo dữ liệu giả cho Worker
  worker1: Worker = {
    workerId: 1001,
    scheduleId: 2001,
    employee: this.employee1,
    quantity: 50,
  };

  worker2: Worker = {
    workerId: 1002,
    scheduleId: 2002,
    employee: this.employee2,
    quantity: 30,
  };

  // Tạo dữ liệu giả cho mảng Attendent
  attendents: Attendent[] = [
    {
      attendentId: 3001,
      worker: this.worker1,
      quantity: 45,
      alpha: 0.9,
    },
    {
      attendentId: 3002,
      worker: this.worker2,
      quantity: 25,
      alpha: 0.8,
    },
  ];

  // Modal visibility
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

  // Toggle Add Modal
  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  // Add Record
  addRecord() {
    if(!this.validateAttendent(this.newAttendent)){
        return;
    }
    // Logic to actually add the record to your data source
    this.toggleAddModal(); // Close the modal after adding
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

  // Toggle Search Modal
  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  // Perform Search
  performSearch() {
    // Filter logic here
    this.toggleSearchModal(); // Close the modal after searching
  }

  titleEdit: string = '';
  openEditModal(attendent: Attendent) {
    this.titleEdit = 'Edit worker schedule ' + attendent.worker.workerId;
    this.editAttendent = { ...attendent };
    this.isEditModalOpen = true;
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.resetErrors();
  }

  saveEdit(){
    if(!this.validateAttendent(this.editAttendent)){
        return;
    }
    //save here
    this.closeEditModal();
  }

  // View All
  viewAll() {
    sessionStorage.removeItem("schedule");
    console.log('View All Records');
    // Logic to fetch and display all records
  }

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

    if (attendent.alpha === null || attendent.alpha === undefined || attendent.alpha < 0) {
      this.errors.alpha = 'Alpha is required and must be number >= 0';
      isValid = false;
    } else {
      this.errors.alpha = null;
    }

    if (
      !attendent.quantity ||
      !Number.isInteger(attendent.quantity) ||
      attendent.quantity < 0
    ) {
      this.errors.actualQuantity =
        'Actual quantity is required and must be natural number >= 0';
      isValid = false;
    } else {
      this.errors.actualQuantity = null;
    }

    return isValid;
  }

  idDelete : number | null = null;
  titleModalDelete: string = '';
  isVisible: boolean = false;

  openModal(attendent: Attendent): void {
    this.titleModalDelete = "Do you want to delete worker schedule " + attendent.worker.workerId;
    this.idDelete = attendent.worker.workerId;
    this.isVisible = true;
  }

  closeModal(): void {
    this.isVisible = false;
  }

  confirmDelete(): void {
    //delete
    this.closeModal();
  }


}

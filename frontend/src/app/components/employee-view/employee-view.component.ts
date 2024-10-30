import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { AccountResponse } from '../../dto/accountResponse';
import { CommonModule } from '@angular/common';
import { Employee } from '../../dto/employee';
import { Department } from '../../dto/department';
import { FormsModule } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';
import { DepartmentService } from '../../service/department.service';
import { EmployeeService } from '../../service/employee.service';
import { permission } from '../../permission/permission';
import { AuthStorageService } from '../../service/authStorage.service';
import { Attendent } from '../../dto/attendent';

@Component({
  selector: 'app-employee-view',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    CommonModule,
    FormsModule,
    RouterOutlet,
  ],
  templateUrl: './employee-view.component.html',
  styleUrl: './employee-view.component.scss',
})
export class EmployeeViewComponent implements OnInit {
  public permission = permission;

  account: AccountResponse | null = null;

  attendent: Attendent | null = null;

  role: string | null = null;

  employees: Employee[] = [];
  departments: Department[] = [];

  isSearchModalOpen: boolean = false;
  searchCriteria: Employee = {
    employeeId: null,
    employeeName: null,
    department: null,
  };

  constructor(
    private departmentService: DepartmentService,
    private employeeService: EmployeeService,
    private router: Router,
    private authService: AuthStorageService
  ) {}

  ngOnInit(): void {
    this.role = this.authService.getPermission();

    this.allDept();

    if (this.role === permission.accountManager) {
      const dataAcc = sessionStorage.getItem('account');
      if (dataAcc) {
        this.account = JSON.parse(dataAcc);
        if (this.account?.employeeId) {
          this.searchCriteria.employeeId = this.account.employeeId;
          this.search();
        }
      } else {
        this.all();
      }
    } else if (this.role === permission.workerScheduleManager) {
      const dataAttendent = sessionStorage.getItem('attendent');
      if (dataAttendent) {
        this.attendent = JSON.parse(dataAttendent);
        if (this.attendent?.worker.employee.employeeId) {
          this.searchCriteria.employeeId =
            this.attendent?.worker.employee.employeeId;
          this.search();
        }
      } else {
        this.all();
      }
    }
  }

  clearAccount() {
    this.account = null;
    sessionStorage.removeItem('account');
    this.all();
  }

  viewDetailEmployee(employee: Employee) {
    sessionStorage.setItem('employee', JSON.stringify(employee));
    sessionStorage.removeItem('account');
    this.router.navigateByUrl('account');
  }

  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  performSearch(event: Event) {
    event.preventDefault();
    this.search();
    this.toggleSearchModal();
  }

  allDept() {
    this.departmentService.all().subscribe({
      next: (response: Department[]) => {
        this.departments = response;
      },
      error: (error: any) => {
        console.log('Error: ' + error.error);
      },
    });
  }

  all() {
    this.employeeService.all().subscribe({
      next: (response: Employee[]) => {
        this.employees = response;
        console.log(response);
      },
      error: (error: any) => {
        console.log('Error: ' + error.error);
      },
    });
  }

  search() {
    this.employeeService.search(this.searchCriteria).subscribe({
      next: (response: Employee[]) => {
        this.employees = response;
      },
      error: (error: any) => {
        console.log('Error: ' + error.error);
      },
    });
  }
}

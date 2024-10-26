import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { Account } from '../../common/accountResponse';
import { CommonModule } from '@angular/common';
import { Employee } from '../../common/employee';
import { Role } from '../../common/role';
import { Department } from '../../common/department';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-employee-view',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CommonModule, FormsModule],
  templateUrl: './employee-view.component.html',
  styleUrl: './employee-view.component.scss',
})
export class EmployeeViewComponent {
  account: Account | null = null;

  constructor() {
    const dataAcc = sessionStorage.getItem('account');
    if (dataAcc) {
      this.account = JSON.parse(dataAcc);
      //return emp set
    } else {
      //get all account
    }
  }

  getRolesAsString(roles: Role[]): string {
    return Array.from(roles)
      .map((role) => role.name)
      .join(', ');
  }

  clearAccount() {
    if (!this.account) {
      return;
    }
    this.account = null;
    sessionStorage.removeItem('account');
    //reload all emp
  }

  viewDetailEmployee(employee: Employee) {
    sessionStorage.setItem('employee', JSON.stringify(employee));
    sessionStorage.removeItem('account');
    // route to another site
  }

  employees: Employee[] = [];

  isSearchModalOpen: boolean = false;
  searchCriteria: Employee = {
    employeeId: null,
    employeeName: null,
    department: null,
  };

  // Danh sách các phòng ban (departments) cho dropdown, sử dụng Set
  departments: Department[] = [
    { departmentId: 1, departmentName: 'HR', departmentType: 'WS' },
    { departmentId: 2, departmentName: 'IT', departmentType: 'WS' },
    { departmentId: 3, departmentName: 'Finance', departmentType: 'WS' },
    // Thêm các phòng ban khác nếu cần
  ];

  // Controls the search modal visibility
  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  // Method to perform the search (you can implement your own search logic here)
  performSearch(event: Event) {
    event.preventDefault();
    console.log(this.searchCriteria);
    console.log(this.searchCriteria.department);
    // Close the search modal after search is performed
    this.toggleSearchModal();
  }
}

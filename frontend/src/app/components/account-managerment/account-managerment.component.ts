import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AccountResponse } from '../../common/accountResponse';
import { AccountRequest } from '../../common/accountRequest';
import { Role } from '../../common/role';
import { Employee } from '../../common/employee';
import { Router } from '@angular/router';

@Component({
  selector: 'app-account-managerment',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CommonModule, FormsModule],
  templateUrl: './account-managerment.component.html',
  styleUrl: './account-managerment.component.scss',
})
export class AccountManagermentComponent {
  employee: Employee | null = null;

  constructor(private router: Router) {
    const dataEmp = sessionStorage.getItem('employee');
    if (dataEmp) {
      this.employee = JSON.parse(dataEmp);
      //get account by employee
    } else {
      //get all account
    }
  }

  clearEmployee() {
    if (!this.employee) {
      return;
    }
    this.employee = null; // Đặt giá trị info thành null
    sessionStorage.removeItem('employee');
    //reload set accoount
  }

  viewDetailAccount(account: Account) {
    sessionStorage.setItem('account', JSON.stringify(account));
    sessionStorage.removeItem('employee');
    //route here
  }

  deleteAccount(account: Account) {
    //call delete
    //refresh list account
  }

  roles: Role[] = [
    { id: 1, name: 'Admin' },
    { id: 2, name: 'User' },
    { id: 3, name: 'Manager' },
  ];

  accounts: Account[] = [
    {
      userId: 101,
      username: 'johnDoe',
      employeeId: 1,
      role: [
        { id: 1, name: 'Admin' },
        { id: 2, name: 'User' },
      ],
      status: 'Blocked',
    },
    {
      userId: 102,
      username: 'janeSmith',
      employeeId: 2,
      role: [{ id: 2, name: 'User' }],
      status: 'Actived',
    },
  ];

  getRolesAsString(roles: Role[]): string {
    return Array.from(roles)
      .map((role) => role.name)
      .join(', ');
  }

  isAddModalOpen: boolean = false;
  newPassword: string = '';
  newUser: Account = {
    userId: null,
    username: null,
    employeeId: null,
    role: [],
    status: 'Blocked', // Password is now handled separately
  };

  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  onPasswordChange(event: any) {
    this.newPassword = event.target.value;
  }

  // Handle checkbox changes for roles using Set<Role>
  onCheckboxChange(role: Role, event: any) {
    if (event.target.checked) {
      this.newUser.role.push(role); // Add the role to the Set
    }
  }

  // Method to add new user
  addUser() {
    if (!this.validateUserInput()) {
      return;
    }

    // Logic to handle user addition (e.g., send to backend)
    console.log('New User added:', this.newUser);
    console.log('Pass: ', this.newPassword);

    // Reset form and close modal
    this.toggleAddModal();
    this.clearAdd();
  }

  clearAdd() {
    this.newPassword = '';
    this.newUser = {
      userId: null,
      username: null,
      employeeId: null,
      role: [],
      status: 'Blocked',
    };
  }

  isSearchModalOpen: boolean = false;
  selectedRoles: Role[] = []; // Using Set instead of array
  searchCriteria: Account = {
    userId: null,
    username: null,
    employeeId: null,
    status: null,
    role: [],
  };

  // Controls the search modal visibility
  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  // Method to handle role selection change
  onRoleChange(role: Role, event: any) {
    if (event.target.checked) {
      this.selectedRoles.push(role); // Add Role object to the Set
    }
    this.searchCriteria.role = this.selectedRoles; // Update searchCriteria with Set<Role>
  }

  // Method to perform the search (you can implement your own search logic here)
  performSearch() {
    console.log('Search criteria:', this.searchCriteria);
    // Close the search modal after search is performed
    this.toggleSearchModal();
  }

  selectedUser: Account = {
    userId: null,
    username: null,
    employeeId: null,
    role: [],
    status: null,
  }; // Object to store selected user's details for editing
  selectedRolesEdit: Role[] = []; // Use Set instead of array
  isEditModalOpen: boolean = false; // Controls the visibility of the edit modal
  titleEdit: string = ''; // Modal title
  changePassword: string = ''; // Placeholder for password changes

  // Method to open the edit modal and load the selected user's information
  openEditModal(account: Account) {
    this.titleEdit = 'Edit user ' + account.username;
    this.selectedUser = { ...account }; // Copy the selected account into the selectedUser object
    this.isEditModalOpen = true; // Open the modal
  }

  // Method to close the edit modal
  closeEditModal() {
    this.isEditModalOpen = false; // Close the modal
    this.clearEdit();
    this.resetErrors();
  }

  // Method to handle role selection changes in the edit form
  onRoleChangeEdit(role: Role, event: any) {
    if (event.target.checked) {
      this.selectedRolesEdit.push(role); // Add the role to the Set
    }
    this.selectedUser.role = this.selectedRolesEdit; // Update the selectedUser's role with the Set
  }

  // Method to save the edited information
  saveEdit() {
    console.log('Updated User:', this.selectedUser);
    console.log('New Password:', this.changePassword);
    // Here you would typically update the account in your backend or database

    // Close the modal after saving changes
    this.closeEditModal();
  }

  clearEdit() {
    this.isEditModalOpen = false; // Close the modal
    this.selectedRolesEdit = []; // Clear the roles Set
    this.selectedUser = {
      userId: null,
      username: null,
      employeeId: null,
      role: [],
      status: null,
    };
    this.titleEdit = '';
    this.changePassword = '';
  }

  //error message
  errors: {
    username: string | null;
    password: string | null;
    employeeId: string | null;
    role: string | null;
    status: string | null;
  } = {
    username: null,
    password: null,
    employeeId: null,
    role: null,
    status: null,
  };

  resetErrors() {
    this.errors = {
      username: null,
      password: null,
      employeeId: null,
      role: null,
      status: null,
    };
  }

  validateUserEdit(): boolean {
    let isValid = true;

    // Kiểm tra vai trò
    if (this.selectedUser.role.length === 0) {
      this.errors.role = 'At least one role must be selected';
      isValid = false;
    } else {
      this.errors.role = null;
    }

    if (!this.selectedUser.status) {
      this.errors.status = 'Status is required';
      isValid = false;
    } else {
      this.errors.status = null;
    }

    return isValid;
  }

  // Kiểm tra input trước khi gửi
  validateUserInput(): boolean {
    let isValid = true;

    // Kiểm tra tên người dùng
    if (!this.newUser.username?.trim()) {
      this.errors.username = 'Username is required.';
      isValid = false;
    } else {
      this.errors.username = null;
    }

    // Kiểm tra mật khẩu
    if (!this.newPassword.trim()) {
      this.errors.password = 'Password is required.';
      isValid = false;
    } else {
      this.errors.password = null;
    }

    // Kiểm tra Employee ID
    if (this.newUser.employeeId === null || !Number.isInteger(this.newUser.employeeId) || this.newUser.employeeId <= 0) {
      this.errors.employeeId =
        'Employee ID must be a positive natural number and not null.';
      isValid = false;
    } else {
      this.errors.employeeId = null;
    }

    // Kiểm tra vai trò
    if (this.newUser.role.length === 0) {
      this.errors.role = 'At least one role must be selected.';
      isValid = false;
    } else {
      this.errors.role = null;
    }

    return isValid;
  }
}

import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AccountResponse } from '../../dto/accountResponse';
import { AccountRequest } from '../../dto/accountRequest';
import { Role } from '../../dto/role';
import { Employee } from '../../dto/employee';
import { Router, RouterOutlet } from '@angular/router';
import { AccountService } from '../../service/account.service';
import { RoleService } from '../../service/role.service';
import { MessageResponse } from '../../dto/messageResponse';

@Component({
  selector: 'app-account-managerment',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    CommonModule,
    FormsModule,
    RouterOutlet,
  ],
  templateUrl: './account-managerment.component.html',
  styleUrl: './account-managerment.component.scss',
})
export class AccountManagermentComponent implements OnInit {
  employee: Employee | null = null;

  roles: Role[] = [];

  accounts: AccountResponse[] = [];

  isAddModalOpen: boolean = false;
  newUser: AccountRequest = {
    userId: null,
    password: null,
    username: null,
    employeeId: null,
    role: null,
    status: 'Blocked',
  };

  isSearchModalOpen: boolean = false;
  searchCriteria: AccountRequest = {
    userId: null,
    username: null,
    password: null,
    employeeId: null,
    status: null,
    role: null,
  };

  selectedUser: AccountRequest = {
    userId: null,
    username: null,
    password: null,
    employeeId: null,
    role: null,
    status: null,
  };
  isEditModalOpen: boolean = false;
  titleEdit: string = '';

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

  constructor(
    private router: Router,
    private accountService: AccountService,
    private roleService: RoleService
  ) {}

  ngOnInit(): void {
    const dataEmp = sessionStorage.getItem('employee');

    if (dataEmp) {
      this.employee = JSON.parse(dataEmp);
      if (this.employee?.employeeId) {
        this.searchCriteria.employeeId = this.employee?.employeeId;
        this.search();
      }
    } else {
      this.listAll();
    }

    this.roleService.getAllRole().subscribe({
      next: (response: Role[]) => {
        this.roles = response;
      },
    });
  }

  clearAdd() {
    this.newUser = {
      userId: null,
      username: null,
      password: null,
      employeeId: null,
      role: null,
      status: 'Blocked',
    };
  }

  clearEmployee() {
    this.employee = null;
    sessionStorage.removeItem('employee');
    this.listAll();
  }

  viewDetailAccount(account: AccountResponse) {
    sessionStorage.setItem('account', JSON.stringify(account));
    sessionStorage.removeItem('employee');
    this.router.navigateByUrl('employee');
  }

  deleteAccount(account: AccountResponse) {
    if (account.userId) {
      this.delete(account.userId);
    }
  }

  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
    this.resetErrors();
  }

  addUser() {
    if (!this.validateUserInput()) {
      return;
    }
    this.create();
    this.toggleAddModal();
    this.clearAdd();
  }

  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  performSearch() {
    this.search();
    this.toggleSearchModal();
  }

  openEditModal(account: AccountResponse) {
    this.titleEdit = 'Edit user ' + account.username;
    this.selectedUser = { ...account, password: null };
    this.isEditModalOpen = true;
  }

  closeEditModal() {
    this.isEditModalOpen = false;
    this.clearEdit();
    this.resetErrors();
  }

  saveEdit() {
    this.update(this.selectedUser);
    this.closeEditModal();
  }

  clearEdit() {
    this.isEditModalOpen = false; // Close the modal
    this.selectedUser = {
      userId: null,
      username: null,
      password: null,
      employeeId: null,
      role: null,
      status: null,
    };
    this.titleEdit = '';
  }

  validateUserEdit(): boolean {
    let isValid = true;

    if (this.selectedUser.role === null || this.selectedUser.role.id === null) {
      this.errors.role = 'One role must be selected';
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

  validateUserInput(): boolean {
    let isValid = true;

    if (
      !this.newUser.username?.trim() ||
      !this.hasNoDiacritics(this.newUser.username)
    ) {
      this.errors.username = 'Username is required and has no diacritic';
      isValid = false;
    } else {
      this.errors.username = null;
    }

    if (
      !this.newUser.password?.trim() ||
      !this.hasNoDiacritics(this.newUser.password)
    ) {
      this.errors.password = 'Password is required  and has no diacritic';
      isValid = false;
    } else {
      this.errors.password = null;
    }

    if (
      this.newUser.employeeId === null ||
      !Number.isInteger(this.newUser.employeeId) ||
      this.newUser.employeeId <= 0
    ) {
      this.errors.employeeId =
        'Employee ID must be a positive natural number and not null';
      isValid = false;
    } else {
      this.errors.employeeId = null;
    }

    if (this.newUser.role === null || this.newUser.role.id === null) {
      this.errors.role = 'One role must be selected';
      isValid = false;
    } else {
      this.errors.role = null;
    }

    return isValid;
  }

  hasNoDiacritics(str: string): boolean {
    const regex = /^[\u0000-\u007F]*$/;
    return regex.test(str);
  }

  listAll() {
    this.accountService.all().subscribe({
      next: (response: AccountResponse[]) => {
        this.accounts = response;
      },
    });
  }

  search() {
    this.accountService.search(this.searchCriteria).subscribe({
      next: (response: AccountResponse[]) => {
        this.accounts = response;
      },
    });
  }

  create() {
    this.accountService.create(this.newUser).subscribe({
      next: (response: AccountResponse) => {
        console.log('Created: ' + response);
        this.listAll();
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }

  delete(id: number) {
    this.accountService.delete(id).subscribe({
      next: (response: MessageResponse) => {
        alert(response.message);
        this.listAll();
      },
    });
  }

  update(acc: AccountRequest) {
    this.accountService.update(acc).subscribe({
      next: (response: AccountResponse) => {
        console.log('Updated: ' + response);
        this.listAll();
      },
      error: (error: any) => {
        alert(error.error);
      },
    });
  }
}

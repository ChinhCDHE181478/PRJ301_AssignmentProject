import { Component, ChangeDetectorRef, OnInit, ChangeDetectionStrategy, AfterViewInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { FormsModule } from "@angular/forms"
import { CommonModule } from '@angular/common';
import { Account } from '../common/account';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-account-managerment',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CommonModule, FormsModule, RouterOutlet],
  templateUrl: './account-managerment.component.html',
  styleUrl: './account-managerment.component.scss',
})

export class AccountManagermentComponent{

    isAddModalOpen: boolean = false;

  roles: string[] = ['Admin', 'User', 'Manager'];

  accounts: Account[] = [
    { username: 'johnDoe', employeeId: 1, role: ['Admin', 'User'], status: 'Blocked' },
    { username: 'janeSmith', employeeId: 2, role: ['User'], status: 'Actived' }
  ];

  newPassword: string = '';
  newUser: Account = {
    username: '',
    employeeId: 0,
    role: [],
    status: 'Blocked'  // Password is now handled separately
  };

  openAddModal() {
    this.isAddModalOpen = true;
  }

  closeAddModal() {
    this.isAddModalOpen = false;
  }

  onPasswordChange(event: any) {
    this.newPassword = event.target.value;
  }

  // Update the newUser object with form input values
  onUserChange(field: keyof Account, event: Event) {
    const inputElement = event.target as HTMLInputElement;
  
    if (field === 'employeeId') {
      this.newUser[field] = +inputElement.value;  // Convert to number
    } else if(field === 'username') {
      this.newUser[field] = inputElement.value;  // Assume string for username/role
    }
  }

  // Handle checkbox changes for roles
  onCheckboxChange(event: any) {
    const role = event.target.value;
    if (event.target.checked) {
      this.newUser.role.push(role);
    } else {
      this.newUser.role = this.newUser.role.filter(r => r !== role);
    }
  }

  // Method to add new user
  addUser() {
    // Logic to handle user addition (e.g., send to backend)
    console.log('New User added:', this.newUser);
    console.log('Pass: ', this.newPassword);

    // Reset form and close modal
    this.newUser = { username: '', employeeId: 0, role: [], status: ''};
    this.closeAddModal();
    this.clearAdd();
  }

    clearAdd(){
      this.newPassword = '';
    this.newUser = {
    username: '',
    employeeId: 0,
    role: [],
    status: 'Blocked' 
  };
    }




  isSearchModalOpen : boolean = false;
  selectedRoles: string[] = [];
  searchCriteria: Account = {
    username: '',
    employeeId: 0,
    status: '',
    role: []
  };
 // Controls the search modal visibility

  // Method to open the search modal
  openSearchModal() {
    this.isSearchModalOpen = true;
  }

  // Method to close the search modal
  closeSearchModal() {
    this.isSearchModalOpen = false;
  }

  // Method to handle input change for search criteria
  onSearchChange(field: string, event: any) {
    if(field === 'employeeId'){
      this.searchCriteria[field] = +event.target.value;
    }
    else if(field === 'username' || field === 'status')
    this.searchCriteria[field] = event.target.value;
  }

  // Method to handle role selection change
  onRoleChange(event: any) {
    const role = event.target.value;
    if (event.target.checked) {
      this.selectedRoles.push(role);
    } else {
      const index = this.selectedRoles.indexOf(role);
      if (index > -1) {
        this.selectedRoles.splice(index, 1);
      }
    }
    this.searchCriteria.role = this.selectedRoles;
  }

  // Method to perform the search (you can implement your own search logic here)
  performSearch() {
    console.log('Search criteria:', this.searchCriteria);

    // Close the search modal after search is performed
    this.closeSearchModal();
  }

  clearSearch(){
    this.selectedRoles = [];
    this.searchCriteria = {
      username: '',
      employeeId: 0,
      status: '',
      role: []
    };
  }




  selectedUser: Account = { username: '', employeeId: 0, role: [], status: ''}; // Object to store selected user's details for editing
  selectedRolesEdit: string[] = [];
  isEditModalOpen: boolean = false; // Controls the visibility of the edit modal
  titleEdit: string = ''; // Modal title
  changePassword: string = ''; // Placeholder for password changes
  
  // Method to open the edit modal and load the selected user's information
  openEditModal(account: Account) {
    this.titleEdit = 'Edit user ' + account.username;
    this.selectedUser = { ...account }; // Copy the selected account into the selectedUser object
    this.selectedUser.role = [];
    this.isEditModalOpen = true; // Open the modal
  }

  // Method to close the edit modal
  closeEditModal() {
    this.isEditModalOpen = false; // Close the modal
    this.clearEdit();
  }

  onEditChange(field: string, event: any) {
    if(field === 'status')
      this.selectedUser[field] = event.target.value;
    else if(field === 'password'){
      this.changePassword = event.target.value;
    }
  }

  // Method to handle role selection changes in the edit form
  onRoleChangeEdit(role: string, event: any) {
    if (event.target.checked) {
      this.selectedRolesEdit.push(role);
    } else {
      const index = this.selectedRolesEdit.indexOf(role);
      if (index > -1) {
        this.selectedRolesEdit.splice(index, 1);
      }
    }
    this.selectedUser.role = this.selectedRolesEdit;
  }

  // Method to save the edited information
  saveEdit() {
    console.log('Updated User:', this.selectedUser);
    console.log('New Password:', this.changePassword);
    // Here you would typically update the account in your backend or database

    // Close the modal after saving changes
    this.clearEdit();
    this.closeEditModal();
  }

  clearEdit(){
    this.isEditModalOpen = false; // Close the modal
    this.selectedRolesEdit = [];
    this.selectedUser = { username: '', employeeId: 0, role: [], status: ''};
    this.titleEdit = '';
    this.changePassword = '';
  }


}

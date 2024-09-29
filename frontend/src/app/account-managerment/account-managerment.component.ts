import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { FormsModule } from "@angular/forms"
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-account-managerment',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CommonModule, FormsModule],
  templateUrl: './account-managerment.component.html',
  styleUrl: './account-managerment.component.scss'
})
export class AccountManagermentComponent {
  isAddModalOpen = false;
  isSearchModalOpen = false;
  newUser = {
    username: '',
    password: '',
    name: '',
    role: ''
  };
  searchParams = {
    id: '',
    username: '',
    name: ''
  };

  // Open and close modals
  openAddModal() {
    this.isAddModalOpen = true;
  }

  closeAddModal() {
    this.isAddModalOpen = false;
  }

  openSearchModal() {
    this.isSearchModalOpen = true;
  }

  closeSearchModal() {
    this.isSearchModalOpen = false;
  }

  // Add user function
  addUser() {
    // Logic to add user goes here
    console.log('User added:', this.newUser);
    this.closeAddModal();
  }

  // Search function
  performSearch() {
    // Logic to search for users based on searchParams
    console.log('Search params:', this.searchParams);
    this.closeSearchModal();
  }
}

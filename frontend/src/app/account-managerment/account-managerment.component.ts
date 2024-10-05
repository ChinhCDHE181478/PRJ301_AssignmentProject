import { Component, ChangeDetectorRef, OnInit } from '@angular/core';
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

export class AccountManagermentComponent implements OnInit{

  accounts: any[]|undefined;

  ngOnInit() {
    this.initializeAccounts();
    console.log(this.accounts)
  }

  initializeAccounts() {
    this.accounts = [
      { username: 'JohnDoe', employeeId: 123, role: 'Admin', status: 'Active' },
      { username: 'JaneDoe', employeeId: 456, role: 'User', status: 'Inactive' }
    ];
  }

  openEditModal(account: any) {
    // Logic để mở modal chỉnh sửa tài khoản
    console.log(account);
  }

  //add model
  isAddModalOpen = false;

  newUser = {
    username: '',
    password: '',
    employeeId: '',
    role: ''
  };
  
  openAddModal() {
    this.isAddModalOpen = true;
  }
  
  closeAddModal() {
    this.isAddModalOpen = false;
  }
  
  onUserChange(field: keyof typeof this.newUser, event: Event) {
    const target = event.target as HTMLInputElement | HTMLSelectElement; // Ép kiểu target
    this.newUser[field] = target.value;
  }
  
  addUser() {
    console.log('User added:', this.newUser);
    this.closeAddModal();
  }


  //search modal
  isSearchModalOpen = false;

  searchParams = {
    username: '',
    employeeId: '',
    role: ''
  };

  openSearchModal() {
    this.isSearchModalOpen = true;
  }

  closeSearchModal() {
    this.isSearchModalOpen = false;
  }

  onSearchChange(field: keyof typeof this.searchParams, event: Event) {
    const target = event.target as HTMLInputElement | HTMLSelectElement; // Ép kiểu target
    this.searchParams[field] = target.value; // Truy cập value
  }

  performSearch() {
    console.log('Search params:', this.searchParams);
    this.closeSearchModal();
  }
  

  isEditModalOpen = false; // Điều khiển mở/đóng modal
    selectedUser = { username: '', password: '', employeeId: '', role: '', status: '' }; // Dữ liệu người dùng được chọn

    // Hàm đóng modal
    closeEditModal(): void {
        this.isEditModalOpen = false;
    }

    // Hàm lưu thay đổi khi chỉnh sửa
    saveEdit(): void {
        // Xử lý lưu thay đổi dữ liệu
        console.log('User data:', this.selectedUser);
        this.isEditModalOpen = false;
    }

}

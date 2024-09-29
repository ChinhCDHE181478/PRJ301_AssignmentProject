import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { HeaderComponent } from "../header/header.component";
import { FormsModule } from "@angular/forms"
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-production-plans',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, FormsModule, CommonModule],
  templateUrl: './production-plans.component.html',
  styleUrl: './production-plans.component.scss'
})
export class ProductionPlansComponent {
  isAddModalOpen = false;

// Add criteria
addID = '';
addName = '';
addStart: Date | null = null;
addEnd: Date | null = null;
addDepartment = '';

toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
}

addRecord() {
    // Your logic to add a new record
    console.log('Adding record with data:', {
        id: this.addID,
        name: this.addName,
        start: this.addStart,
        end: this.addEnd,
        department: this.addDepartment
    });

    this.toggleAddModal(); // Close the modal after adding
}

  isSearchModalOpen: boolean = false;
  viewDetails(id: number) {
    console.log(`Viewing details for ID: ${id}`);
  }

  search() {
    // Thực hiện tìm kiếm
    console.log('Search button clicked');
  }

  viewAll() {
    // Hiển thị tất cả sản phẩm
    console.log('View All button clicked');
  }


  // Dữ liệu mẫu cho bảng
  items = [
    { id: '001', name: 'Product A', start: '2024-09-01', end: '2024-09-05', department: 'Production' },
    { id: '002', name: 'Product B', start: '2024-09-03', end: '2024-09-10', department: 'Marketing' },
    // Thêm dữ liệu khác ở đây
  ];

  filteredItems = [...this.items]; // Mảng hiển thị sẽ được lọc dựa trên tìm kiếm

  // Các biến để lưu thông tin nhập vào form tìm kiếm
  searchID: string = '';
  searchName: string = '';
  searchStart: string = '';
  searchEnd: string = '';
  searchDepartment: string = '';

  // Hàm để bật/tắt modal
  toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  // Hàm thực hiện tìm kiếm
  performSearch() {
    // Lọc items dựa trên thông tin tìm kiếm

    // Ẩn modal sau khi tìm kiếm
    this.toggleSearchModal();
  }

  // Hàm để hủy tìm kiếm và đóng modal
  cancelSearch() {
    this.toggleSearchModal();
    // Reset các trường tìm kiếm
    this.searchID = '';
    this.searchName = '';
    this.searchStart = '';
    this.searchEnd = '';
    this.searchDepartment = '';
  }
}

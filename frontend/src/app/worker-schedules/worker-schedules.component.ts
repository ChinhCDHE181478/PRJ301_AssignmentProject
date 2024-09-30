import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-worker-schedules',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule,CommonModule],
  templateUrl: './worker-schedules.component.html',
  styleUrl: './worker-schedules.component.scss'
})
export class WorkerSchedulesComponent {
  // Modal visibility
  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false;
  isSearchInTableModalOpen = false;

  // Add criteria
  addScheduleID = '';
  addEmployeeID = '';
  addEmployeeName = '';
  addQuantity = 0;

  // Search criteria
  searchScheduleID = '';
  searchEmployeeID = '';
  searchEmployeeName = '';
  searchQuantity = 0;

  // Search criteria for the table
  searchInTableScheduleID = '';
  searchInTableEmployeeID = '';
  searchInTableEmployeeName = '';
  searchInTableQuantity: number | null = null;

  // Toggle Add Modal
  toggleAddModal() {
      this.isAddModalOpen = !this.isAddModalOpen;
  }

  // Add Record
  addRecord() {
      console.log('Adding record with data:', {
          scheduleID: this.addScheduleID,
          employeeID: this.addEmployeeID,
          employeeName: this.addEmployeeName,
          quantity: this.addQuantity
      });

      // Logic to actually add the record to your data source
      this.toggleAddModal(); // Close the modal after adding
  }

  // Toggle Search Modal
  toggleSearchModal() {
      this.isSearchModalOpen = !this.isSearchModalOpen;
  }

  // Perform Search
  performSearch() {
      console.log('Searching with criteria:', {
          scheduleID: this.searchScheduleID,
          employeeID: this.searchEmployeeID,
          employeeName: this.searchEmployeeName,
          quantity: this.searchQuantity
      });

      // Filter logic here
      this.toggleSearchModal(); // Close the modal after searching
  }

  // Toggle Search in Table Modal
  toggleSearchInTableModal() {
      this.isSearchInTableModalOpen = !this.isSearchInTableModalOpen;
  }

  // Perform Search in Table
  performSearchInTable() {
      console.log('Searching in table with criteria:', {
          scheduleID: this.searchInTableScheduleID,
          employeeID: this.searchInTableEmployeeID,
          employeeName: this.searchInTableEmployeeName,
          quantity: this.searchInTableQuantity
      });

      // Table-specific filter logic here
      this.toggleSearchInTableModal(); // Close the modal after searching
  }

  // View All
  viewAll() {
      console.log('View All Records');
      // Logic to fetch and display all records
  }
}

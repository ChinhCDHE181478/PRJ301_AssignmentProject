import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-attendent',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule,CommonModule],
  templateUrl: './attendent.component.html',
  styleUrl: './attendent.component.scss'
})
export class AttendentComponent {
  isAddModalOpen: boolean = false;
  isSearchModalOpen: boolean = false;
  isSearchInTableModalOpen: boolean = false;

  // Add criteria
  addAttendentID = '';
  addWorkerID = '';
  addQuantity = 0;
  addAlpha = '';

  // Search criteria
  searchAttendentID = '';
  searchWorkerID = '';
  searchQuantity = 0;
  searchAlpha = '';

  // Search criteria for the table
  searchInTableAttendentID = '';
  searchInTableWorkerID = '';
  searchInTableQuantity: number | null = null;
  searchInTableAlpha = '';

  // Toggle Add Modal
  toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
  }

  // Add Record
  addRecord() {
    console.log('Adding record with data:', {
      attendentID: this.addAttendentID,
      workerID: this.addWorkerID,
      quantity: this.addQuantity,
      alpha: this.addAlpha
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
      attendentID: this.searchAttendentID,
      workerID: this.searchWorkerID,
      quantity: this.searchQuantity,
      alpha: this.searchAlpha
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
      attendentID: this.searchInTableAttendentID,
      workerID: this.searchInTableWorkerID,
      quantity: this.searchInTableQuantity,
      alpha: this.searchInTableAlpha
    });

    // Table-specific filter logic here
    this.toggleSearchInTableModal(); // Close the modal after searching
  }

  // View All Records
  viewAll() {
    console.log('View All Records');
    // Logic to fetch and display all records
  }
}

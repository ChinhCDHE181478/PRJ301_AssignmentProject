import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-schedule-campaigns',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, FormsModule, CommonModule],
  templateUrl: './schedule-campaigns.component.html',
  styleUrl: './schedule-campaigns.component.scss'
})
export class ScheduleCampaignsComponent {

  isAddModalOpen: boolean = false;
isSearchModalOpen: boolean = false;  // <-- Initialize this
isSearchInTableModalOpen = false;
  // Add criteria
addID = '';
addCampaignID = '';
addDate: Date | null = null;
addShiftName = '';
addQuantity = 0;

toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
}

addRecord() {
    console.log('Adding record with data:', {
        id: this.addID,
        campaignID: this.addCampaignID,
        date: this.addDate,
        shiftName: this.addShiftName,
        quantity: this.addQuantity
    });

    this.toggleAddModal(); // Close the modal after adding
}

// Search criteria
searchID = '';
searchCampaignID = '';
searchDate: string = '';
searchShiftName: string = '';
searchQuantity: string = '';

toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
}

performSearch() {
    // Filter logic here

    this.toggleSearchModal();
}

viewAll() {
    console.log('View All button clicked');
}
// Search criteria for the table
searchInTableCampaignID = '';
searchInTablePlanID = '';
searchInTableProduct = '';
searchInTableQuantity: number | null = null;
searchInTableUnitEffort: number | null = null;

toggleSearchInTableModal() {
  this.isSearchInTableModalOpen = !this.isSearchInTableModalOpen;
}

performSearchInTable() {
  // Your search logic for the table
  console.log('Searching in table with criteria:', {
      campaignID: this.searchInTableCampaignID,
      planID: this.searchInTablePlanID,
      product: this.searchInTableProduct,
      quantity: this.searchInTableQuantity,
      unitEffort: this.searchInTableUnitEffort
  });

  this.toggleSearchInTableModal(); // Close the modal after searching
}
}

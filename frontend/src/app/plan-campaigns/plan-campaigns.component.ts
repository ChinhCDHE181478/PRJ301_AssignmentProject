import { Component, ViewChild, ElementRef } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { FormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-plan-campaigns',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CommonModule, FormsModule],
  templateUrl: './plan-campaigns.component.html',
  styleUrls: ['./plan-campaigns.component.scss']
})
export class PlanCampaignsComponent {

    viewAll() {
        // Hiển thị tất cả sản phẩm
        console.log('View All button clicked');
      }

    isAddModalOpen = false;

// Add criteria
addID = '';
addPlanID = '';
addProduct = '';
addQuantity: number | null = null;
addUnitEffort: number | null = null;

toggleAddModal() {
    this.isAddModalOpen = !this.isAddModalOpen;
}

addRecord() {
    // Your logic to add a new record
    console.log('Adding record with data:', {
        id: this.addID,
        planID: this.addPlanID,
        product: this.addProduct,
        quantity: this.addQuantity,
        unitEffort: this.addUnitEffort
    });

    this.toggleAddModal(); // Close the modal after adding
}
    isSearchModalOpen = false;
isSearchInTableModalOpen = false;

// Search criteria for the main search modal
searchID = '';
searchPlanID = '';
searchProduct = '';
searchQuantity: number | null = null;
searchUnitEffort: number | null = null;

// Search criteria for the "Search in This Table" modal
searchInTableID = '';
searchInTablePlanID = '';
searchInTableProduct = '';
searchInTableQuantity: number | null = null;
searchInTableUnitEffort: number | null = null;

toggleSearchModal() {
    this.isSearchModalOpen = !this.isSearchModalOpen;
}

toggleSearchInTableModal() {
    this.isSearchInTableModalOpen = !this.isSearchInTableModalOpen;
}

performSearch() {
    // Your search logic here
    console.log('Searching with criteria:', {
        id: this.searchID,
        planID: this.searchPlanID,
        product: this.searchProduct,
        quantity: this.searchQuantity,
        unitEffort: this.searchUnitEffort
    });
    
    this.toggleSearchModal(); // Close the modal after searching
}

performSearchInTable() {
    // Your search logic for the table here
    console.log('Searching in table with criteria:', {
        id: this.searchInTableID,
        planID: this.searchInTablePlanID,
        product: this.searchInTableProduct,
        quantity: this.searchInTableQuantity,
        unitEffort: this.searchInTableUnitEffort
    });

    this.toggleSearchInTableModal(); // Close the modal after searching
}
}

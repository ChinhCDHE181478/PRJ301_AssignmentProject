import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";

@Component({
  selector: 'app-employee-view',
  standalone: true,
  imports: [HeaderComponent, FooterComponent],
  templateUrl: './employee-view.component.html',
  styleUrl: './employee-view.component.scss'
})
export class EmployeeViewComponent {

  openSearchModal(){
    
  }

}

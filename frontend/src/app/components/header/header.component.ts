import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  isNavbarOpen = false; // Trạng thái của menu

  toggleNavbar() {
    this.isNavbarOpen = !this.isNavbarOpen; // Đổi trạng thái mở/đóng menu
  }
}

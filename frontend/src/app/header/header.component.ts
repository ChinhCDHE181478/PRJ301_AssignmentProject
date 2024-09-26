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
  isOpen: boolean = false; // Biến để kiểm soát trạng thái mở/đóng của sidebar

  toggleSidebar() {
    this.isOpen = !this.isOpen; // Đảo ngược trạng thái
  }

  closeSidebar() {
    this.isOpen = false; // Đóng sidebar
  }
}

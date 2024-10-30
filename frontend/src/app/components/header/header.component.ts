import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { AuthStorageService } from '../../service/authStorage.service';
import { permission } from '../../permission/permission';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  public permission = permission;

  role: string | null = null;

  constructor(
    private authService: AuthStorageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.role = this.authService.getPermission();
  }

  isNavbarOpen = false; // Trạng thái của menu

  toggleNavbar() {
    this.isNavbarOpen = !this.isNavbarOpen; // Đổi trạng thái mở/đóng menu
  }

  logOut() {
    this.authService.clearAuthData();
    sessionStorage.clear();
    this.router.navigateByUrl('login');
  }
}

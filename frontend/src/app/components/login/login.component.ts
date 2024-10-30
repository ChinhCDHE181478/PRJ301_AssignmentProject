import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';
import { AccountService } from '../../service/account.service';
import { UserLoginRequest } from '../../dto/userLoginRequest';
import { UserLoginResponse } from '../../dto/userLoginResponse';
import { AuthStorageService } from '../../service/authStorage.service';
import { permission } from '../../permission/permission';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterOutlet],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  constructor(
    private router: Router,
    private accountService: AccountService,
    private authStorageService: AuthStorageService
  ) {}

  userLogin: UserLoginRequest = {
    username: '',
    password: '',
  };

  login() {
    if (!this.validateUser()) {
      return;
    }
    this.accountService.login(this.userLogin).subscribe({
      next: (response: UserLoginResponse) => {
        this.authStorageService.setToken(response.token);
        this.authStorageService.setPermission(response.role);
        if (response.role === permission.accountManager) {
          this.router.navigateByUrl('account');
        } else {
          this.router.navigateByUrl('plan');
        }
      },
      error: (error: any) => {
        alert(`Cannot login, error: ${error.error}`);
      },
    });
  }

  errors: UserLoginRequest = {
    username: '',
    password: '',
  };

  validateUser(): boolean {
    let isValid = true;

    if (!this.userLogin.username.trim()) {
      this.errors.username = 'Username is required';
      isValid = false;
    } else {
      this.errors.password = '';
    }

    if (!this.userLogin.password.trim()) {
      this.errors.password = 'Password is required';
      isValid = false;
    } else {
      this.errors.password = '';
    }

    return isValid;
  }
}

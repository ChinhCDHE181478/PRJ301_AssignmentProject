import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthStorageService {
  private readonly TOKEN_KEY = 'access_token';
  private readonly PERMISSION_KEY = 'access_permission';

  constructor() {}

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  setToken(token: string) {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  removeToken() {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  getPermission(): string | null {
    return localStorage.getItem(this.PERMISSION_KEY);
  }

  setPermission(permission: string) {
    localStorage.setItem(this.PERMISSION_KEY, permission);
  }

  removePermission() {
    localStorage.removeItem(this.PERMISSION_KEY);
  }

  clearAuthData() {
    this.removeToken();
    this.removePermission();
  }
}

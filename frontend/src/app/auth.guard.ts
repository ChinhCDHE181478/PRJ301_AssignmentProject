import { CanActivateFn, Router } from '@angular/router';
import { AuthStorageService } from './service/authStorage.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  
  const auth = inject(AuthStorageService);

  if(!auth.getToken()) {
    inject(Router).navigateByUrl('login');
    return false;
  }

  return true;
};

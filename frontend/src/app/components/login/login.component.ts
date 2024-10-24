import { Component } from '@angular/core';
import { UserLogin } from '../../common/userLogin';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  userLogin: UserLogin = {
    username: "",
    password: ""
  }

  login(){
    if(!this.validateUser()){
      return;
    }
    //login
  }

  errors: UserLogin = {
    username: "",
    password: ""
  }

  validateUser(): boolean{
    let isValid = true;

    if(!this.userLogin.username.trim()){
      this.errors.username = "Username is required";
      isValid = false;
    } else {
      this.errors.password = "";
    }

    if(!this.userLogin.password.trim()){
      this.errors.password = "Password is required";
      isValid = false;
    } else {
      this.errors.password = "";
    }

    return isValid;
  }
}

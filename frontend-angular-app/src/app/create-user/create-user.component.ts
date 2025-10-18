import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-create-user',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.scss'
})
export class CreateUserComponent {
  userForm: FormGroup;
  private baseUrl = "http://localhost:8083/"


  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      role: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      this.http.post(this.baseUrl+'users', this.userForm.value) // adjust URL if needed
          .subscribe({
            next: (res) => {
              console.log('User created', res);
              alert('User created successfully!');
              this.userForm.reset();
            },
            error: (err) => {
              console.error('Error creating user', err);
              alert('Failed to create user.');
            }
          });
    }
  }
}

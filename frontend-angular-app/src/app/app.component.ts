import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KeycloakService } from './keycloak.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="p-4">
      <h1>Angular 19 + Keycloak (with OneLogin IdP)</h1>
      <button (click)="getAccessToken()">Get Access Token</button>
      <button (click)="logout()">Logout</button>

      <pre *ngIf="token">{{ token }}</pre>
    </div>

    <div>
      <input #username placeholder="Enter username" />
      <button (click)="login(username.value)">Login</button>
    </div>
  `
})
export class AppComponent {
  token: string | null = null;

  constructor(private keycloak: KeycloakService) {}

  login(username: string) {
    const userShouldGoToOneLogin = true; // your logic
    this.keycloak.init().then(r => true);
  }

  getAccessToken() {
    this.keycloak.getToken().then(tok => {
      this.token = tok;
      console.log('Access Token:', tok);
    });
  }

  logout() {
    this.keycloak.logout();
  }
}

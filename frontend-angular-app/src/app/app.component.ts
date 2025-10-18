import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { KeycloakService } from './keycloak.service';
import Keycloak, {KeycloakLoginOptions} from "keycloak-js";
import {HttpClient} from "@angular/common/http";
import {CreateUserComponent} from "./create-user/create-user.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, CreateUserComponent],
  templateUrl: './app.component.html',

})
export class AppComponent {
  private baseUrl = "http://localhost:8083/"
 /* token: string | null = null;

  constructor(private keycloak: KeycloakService) {}

  login(username: string) {
    const userShouldGoToOneLogin = true; // your logic
    this.keycloak.init().then(r => true);
  }

  async loginWithIdp(idpAlias: string): Promise<void> {
    const loginOptions: KeycloakLoginOptions = {
      idpHint: idpAlias,
    };

    await this.keycloak.loginIdp(loginOptions);
  }

  async loginStandard(): Promise<void> {
    await this.keycloak.login();
  }
  getAccessToken() {
    this.keycloak.getToken().then(tok => {
      this.token = tok;
      console.log('Access Token:', tok);
    });
  }

  logout() {
    this.keycloak.logout();
  }*/

  // Inject the raw Keycloak client instance
  private readonly keycloak = inject(Keycloak);
  private readonly httpClient = inject(HttpClient);

  token = this.keycloak.token;
  responseData: String = "";

  async loginWithIdp(idpAlias: string): Promise<void> {

    try {
      await this.keycloak.login({
        idpHint: 'onelogin',
      });
    } catch (error) {
      console.error('Login failed:', error);
    }
  }

  async login(userName: string): Promise<void> {

    try {
      await this.keycloak.login({
        loginHint: userName
      }).then(data => {

      });
    } catch (error) {
      console.error('Login failed:', error);
    }
  }

  async logout(): Promise<void> {
    try {
      await this.keycloak.logout();
    } catch (error) {
      console.error('Logout failed:', error);
    }
  }

  getDataFromResourceServer() {
    this.httpClient.get(this.baseUrl+'home').subscribe(
        {
          next: (value: object) => {
            console.log(value)
            this.responseData = value.toString();
          }
        }
    )
  }
}

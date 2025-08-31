import { Injectable } from '@angular/core';
import Keycloak, { KeycloakInstance } from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private keycloak: KeycloakInstance;

  constructor() {
    this.keycloak = new Keycloak({
      url: 'http://localhost:8081', // base Keycloak URL
      realm: 'spring-boot-app-realm',                      // your realm
      clientId: 'angular-app'                // client you created in Keycloak
    });
  }

  init(): Promise<boolean> {
    return this.keycloak.init({
      onLoad: 'login-required',
      checkLoginIframe: false,
      redirectUri: window.location.origin
    });
  }

  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (this.keycloak.token) {
        this.keycloak.updateToken(30).then(() => {
          resolve(this.keycloak.token as string);
        }).catch(() => {
          reject('Failed to refresh token');
        });
      } else {
        reject('No token available');
      }
    });
  }

  logout(): void {
    this.keycloak.logout({
      redirectUri: window.location.origin
    });
  }
}

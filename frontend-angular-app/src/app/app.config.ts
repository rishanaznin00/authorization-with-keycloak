import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {
  CUSTOM_BEARER_TOKEN_INTERCEPTOR_CONFIG,
  customBearerTokenInterceptor,
  provideKeycloak
} from "keycloak-angular";
import {provideHttpClient, withInterceptors} from "@angular/common/http";

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes),
    provideHttpClient(withInterceptors([customBearerTokenInterceptor])),
    {
      provide: CUSTOM_BEARER_TOKEN_INTERCEPTOR_CONFIG,
      useValue: [
        {
          shouldAddToken: async (req: { url: string; }, _: any, keycloak: { authenticated: any; }) => {
            return !req.url.startsWith('/public') && keycloak.authenticated;
          }
        }
      ]
    },
    provideKeycloak({
      config: {
        url: 'http://localhost:8081', // base Keycloak URL
        realm: 'spring-boot-app-realm',                      // your realm
        clientId: 'angular-app'
      },
      initOptions: {
        onLoad: 'check-sso',
      },
    }),
  ]
};

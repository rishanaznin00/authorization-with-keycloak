import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { KeycloakService } from './app/keycloak.service';
import {appConfig} from "./app/app.config";


bootstrapApplication(AppComponent, appConfig).catch(err => console.error(err));

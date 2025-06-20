// src/app/app.config.ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { HttpClientModule } from '@angular/common/http'; // Import HttpClientModule
import { importProvidersFrom } from '@angular/core'; // Import importProvidersFrom
import { ReactiveFormsModule } from '@angular/forms'; // Import ReactiveFormsModule

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    importProvidersFrom(HttpClientModule), // Add HttpClientModule here
    importProvidersFrom(ReactiveFormsModule) // Add ReactiveFormsModule here
  ]
};
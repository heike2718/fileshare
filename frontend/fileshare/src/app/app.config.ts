import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { appRoutes } from './app.routes';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { authDataProvider } from '@shared/auth/api';
import { configuration, FS_CONFIGURATION } from '@config';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import {
    HTTP_INTERCEPTORS,
    provideHttpClient,
    withInterceptorsFromDi,
    withXsrfConfiguration,
} from '@angular/common/http';
import { FileshareAPIInterceptor } from './core/interceptors/fileshare-api.interceptor';
import { LoadingInterceptor } from './core/interceptors/loading.interceptor';

// Environment-spezifische Provider
function getEnvironmentSpecificProviders() {
    const providers = [];

    if (!configuration.production) {
        providers.push(
            provideStoreDevtools({
                maxAge: 25,
                connectInZone: true,
                logOnly: false,
            })
        );
    }

    return providers;
}

export const appConfig: ApplicationConfig = {
    providers: [
        provideBrowserGlobalErrorListeners(),
        provideRouter(appRoutes),
        provideRouter(appRoutes),
        provideStore({}),
        provideEffects(),
        authDataProvider,
        ...getEnvironmentSpecificProviders(),
        provideHttpClient(
            withInterceptorsFromDi(),
            withXsrfConfiguration({
                cookieName: 'XSRF-TOKEN',
                headerName: 'X-XSRF-TOKEN',
            })
        ),
        { provide: FS_CONFIGURATION, useValue: configuration },
        { provide: HTTP_INTERCEPTORS, multi: true, useClass: FileshareAPIInterceptor },
        { provide: HTTP_INTERCEPTORS, multi: true, useClass: LoadingInterceptor },
    ],
};

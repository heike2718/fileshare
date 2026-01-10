import { InjectionToken } from '@angular/core';

export interface FSConfiguration {
    readonly production: boolean;
    readonly version: string;
    readonly environment: string;
    readonly apiUrl: string;
}

export const FS_CONFIGURATION = new InjectionToken<FSConfiguration>('fs-configuration');

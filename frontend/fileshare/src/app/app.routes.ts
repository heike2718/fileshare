import { Route } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { authorizedGuard } from '@shared/auth/api';
import { FilesListComponent } from './files/features/files-list/files-list.component';

export const appRoutes: Route[] = [
    {
        path: 'home',
        component: HomeComponent,
    },
    {
        path: 'files',
        canActivateChild: [authorizedGuard('ADMIN')],
        children: [
            { path: '', component: FilesListComponent },
            //{ path: ':uuid', component: EditGefaesstypComponent },
        ],
    },
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
    },
    {
        path: '**',
        redirectTo: 'home',
    },
];

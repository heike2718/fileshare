import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { Navbar } from './layout/navbar/navbar';
import { Sidenav } from './layout/sidenav/sidenav';
import { LoadingIndicatorComponent, MessageComponent } from '@shared/components';
import { AuthFacade } from '@shared/auth/api';

@Component({
    imports: [
        MatToolbarModule,
        MatSidenavModule,
        Navbar,
        Sidenav,
        MessageComponent,
        LoadingIndicatorComponent,
        RouterModule,
    ],
    selector: 'fs-root',
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
    #authFacade = inject(AuthFacade);

    ngOnInit(): void {
        this.#authFacade.initClearOrRestoreSession();
    }
}

import { ChangeDetectionStrategy, Component, EventEmitter, inject, Output } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { MatMenuModule } from '@angular/material/menu';
import { FS_CONFIGURATION, FSConfiguration } from '@config';
import { AuthFacade } from '@shared/auth/api';
import { Router, RouterLinkWithHref } from '@angular/router';
import { map, shareReplay } from 'rxjs';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AsyncPipe } from '@angular/common';

@Component({
    selector: 'fs-navbar',
    imports: [
        MatMenuModule,
        MatIconModule,
        MatListModule,
        MatToolbarModule,
        MatTooltipModule,
        RouterLinkWithHref,
        AsyncPipe,
    ],
    templateUrl: './navbar.html',
    styleUrl: './navbar.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Navbar {
    readonly config: FSConfiguration = inject(FS_CONFIGURATION);

    @Output()
    sidenavToggle = new EventEmitter();

    authFacade = inject(AuthFacade);

    #breakpointObserver = inject(BreakpointObserver);
    #router = inject(Router);

    isHandset$ = this.#breakpointObserver.observe(Breakpoints.Handset).pipe(
        map(result => result.matches),
        shareReplay()
    );

    onToggleSidenav(): void {
        this.sidenavToggle.emit();
    }

    onMenuItemClick(id: number): void {
        this.#router.navigate(['/home', id]);
    }

    login(): void {
        this.authFacade.login();
    }

    logout(): void {
        this.authFacade.logout();
    }
}

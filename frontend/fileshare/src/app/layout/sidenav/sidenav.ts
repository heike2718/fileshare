import { ChangeDetectionStrategy, Component, EventEmitter, inject, Output } from '@angular/core';
import { FS_CONFIGURATION, FSConfiguration } from '@config';
import { AuthFacade } from '@shared/auth/api';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterLinkWithHref } from '@angular/router';
import { AsyncPipe } from '@angular/common';

@Component({
    selector: 'fs-sidenav',
    imports: [
        MatButtonModule,
        MatIconModule,
        MatListModule,
        MatToolbarModule,
        MatTooltipModule,
        MatSidenavModule,
        RouterLinkWithHref,
        AsyncPipe,
    ],
    templateUrl: './sidenav.html',
    styleUrl: './sidenav.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Sidenav {
    readonly config: FSConfiguration = inject(FS_CONFIGURATION);
    authFacade = inject(AuthFacade);

    @Output()
    sidenavClose = new EventEmitter();

    public onSidenavClose = () => {
        this.sidenavClose.emit();
    };

    public login(): void {
        this.authFacade.login();
    }

    public logout(): void {
        this.authFacade.logout();
    }
}

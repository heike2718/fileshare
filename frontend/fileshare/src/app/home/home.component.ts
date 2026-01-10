import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { AuthFacade } from '@shared/auth/api';
import { map, shareReplay } from 'rxjs';

@Component({
    selector: 'fs-home',
    imports: [MatButtonModule, AsyncPipe],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
})
export class HomeComponent {
    #router = inject(Router);
    #breakpointObserver = inject(BreakpointObserver);

    notAuthorizedMessage =
        'Vielen Dank für Ihren Besuch, aber Sie sind leider nicht autorisiert für diese Webseite. Loggen Sie sich am besten einfach wieder aus.';

    authFacade = inject(AuthFacade);

    isHandset$ = this.#breakpointObserver.observe(Breakpoints.Handset).pipe(
        map(result => result.matches),
        shareReplay()
    );

    login(): void {
        this.authFacade.login();
    }

    logout(): void {
        this.authFacade.logout();
    }

    goToFiles(): void {
        this.#router.navigateByUrl('/files');
    }
}

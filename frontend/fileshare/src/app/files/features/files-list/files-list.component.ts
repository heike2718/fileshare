import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
    selector: 'fs-files',
    imports: [],
    templateUrl: './files-list.component.html',
    styleUrl: './files-list.component.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FilesListComponent {}

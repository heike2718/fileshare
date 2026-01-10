import { HttpErrorResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { ErrorService } from './error.service';
import { ErrorType } from '@core/model';

describe('ErrorService', () => {
    let errorService: ErrorService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [ErrorService],
        });

        errorService = TestBed.inject(ErrorService);
        vi.resetAllMocks();
    });

    it('handles JSON', () => {
        const expectedErrorType: ErrorType = 'VALIDATION';

        const httpError = new HttpErrorResponse({
            status: 400,
            error: {
                errorLevel: 'ERROR',
                message: 'Unzulässige Eingabe.',
            },
        });

        const result = errorService.toFileshareError(httpError);

        expect(result).toBeDefined();
        expect(result.type).toBe(expectedErrorType);
    });

    it('handles text', () => {
        const expectedErrorType: ErrorType = 'VALIDATION';

        const httpError = new HttpErrorResponse({
            status: 400,
            error: {
                errorLevel: 'ERROR',
                message: 'Unzulässige Eingabe.',
            },
        });

        const result = errorService.toFileshareError(httpError);

        expect(result).toBeDefined();
        expect(result.type).toBe(expectedErrorType);
    });

    it('handles ohne error', () => {
        const expectedErrorType: ErrorType = 'SERVER';

        const httpError = new HttpErrorResponse({
            status: 0,
        });

        const result = errorService.toFileshareError(httpError);

        expect(result).toBeDefined();
        expect(result.message).toBe(
            'Ups, da ist ein unerwarteter Fehler aufgetreten. Bitte wende Dich vertrauensvoll an Deinen technischen Support.'
        );
        expect(result.type).toBe(expectedErrorType);
    });

    it('allgemeiner error', () => {
        const expectedErrorType: ErrorType = 'SERVER';

        const error = new Error('boom!');

        const result = errorService.toFileshareError(error);

        expect(result).toBeDefined();
        expect(result.message).toBe('boom!');
        expect(result.type).toBe(expectedErrorType);
    });
});

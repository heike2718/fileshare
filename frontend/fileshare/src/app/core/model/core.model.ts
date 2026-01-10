export type ErrorType = 'VALIDATION' | 'NOT_FOUND' | 'DUPLICATE' | 'SERVER' | 'SESSION_EXPIRED';

export interface FileshareError {
    readonly message: string;
    readonly type: ErrorType;
}

export type MessageType = 'info' | 'warn' | 'error';

export interface AppMessage {
    type: MessageType;
    text: string;
    // optional:
    dismissAfterMs?: number; // nur für info
}

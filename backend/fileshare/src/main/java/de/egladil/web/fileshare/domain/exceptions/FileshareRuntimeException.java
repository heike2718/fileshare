// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.exceptions;

public class FileshareRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Kaeuzchenlager runtime exception.
     *
     * @param message the message
     */
    public FileshareRuntimeException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Kaeuzchenlager runtime exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public FileshareRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

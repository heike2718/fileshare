// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.exceptions;

public class UnsupportedVersionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Unsupported version exception.
     *
     * @param message the message
     */
    public UnsupportedVersionException(final String message) {
        super(message);
    }
}

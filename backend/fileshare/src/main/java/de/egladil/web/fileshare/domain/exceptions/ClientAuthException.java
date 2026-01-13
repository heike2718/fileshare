// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.exceptions;

public class ClientAuthException extends RuntimeException {

    public ClientAuthException() {
    }

    public ClientAuthException(String message) {
        super(message);
    }
}

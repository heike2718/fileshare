//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.exceptions;

public class InacceptablePayloadException extends RuntimeException{

  public InacceptablePayloadException(String message) {
    super(message);
  }
}

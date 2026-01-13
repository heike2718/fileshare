//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.exceptions;

public class FileExistsException extends RuntimeException {

  public FileExistsException(String message) {
    super(message);
  }
}

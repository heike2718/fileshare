//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.validation;

public final class ValidationPatternsAndMessages {

  /** The constant INPUT_SECURED. */
  public static final String INPUT_SECURED = "^[a-zA-ZäöüÄÖÜß0-9\\s\"'_\\-.,:;()]*$";

  /** The constant INPUT_SECURED. */
  public static final String FILENAME = "^[a-zA-Z0-9_\\-.]*$";

  /** The constant INVALID_INPUT_MESSAGE_DETAILS. */
  public static final String INVALID_INPUT_MESSAGE_DETAILS = "Erlaubt sind Buchstaben, Ziffern, Leerzeichen, und die Sonderzeichen ( ) , ; _ - \" . :"
      + " Wenn das nicht ausreicht, bitte an die Entwicklung wenden.";
}

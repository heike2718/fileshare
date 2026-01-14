// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.validation;

public final class ValidationPatternsAndMessages {

    public static final String VALID_ID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static final String INPUT_SECURED = "^[a-zA-ZäöüÄÖÜß0-9\\s\"'_\\-.,:;()]*$";

    public static final String INVALID_FILENAME_MESSAGE_DETAILS = "Erlaubt sind ASCII-Buchstaben, Ziffern und die Sonderzeichen . _ -. Minus darf nicht am Anfang stehen. Länge max = 255";

    public static final String INVALID_ID_MESSAGE = "id enthält ungültige Zeichen (muss eine UUID-4 sein)";
}

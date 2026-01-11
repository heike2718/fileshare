//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.validation;

import de.egladil.web.fileshare.domain.core.FileshareConfig;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MaxBase64SizeValidator implements ConstraintValidator<MaxBase64Size, String> {

  // Striktes, einzeiliges Base64 (kein Whitespace, kein MIME)
  private static final Pattern BASE64 = Pattern.compile("^[A-Za-z0-9+/]*={0,2}$");

  @Inject
  FileshareConfig uploadConfig;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return false;
    }

    // kein data:-URI
    if (value.startsWith("data:")) {
      return false;
    }

    // Base64-Form prüfen
    if (!BASE64.matcher(value).matches()) {
      return false;
    }

    int len = value.length();

    // Base64 muss durch 4 teilbar sein
    if ((len & 3) != 0) {
      return false;
    }

    // Padding zählen
    int padding = 0;
    if (len >= 1 && value.charAt(len - 1) == '=') padding++;
    if (len >= 2 && value.charAt(len - 2) == '=') padding++;

    // Exakte Berechnung der decodierten Größe
    long decodedBytes = ((long) len / 4L) * 3L - padding;

    return decodedBytes > 0 && decodedBytes <= uploadConfig.maxBytes();
  }
}

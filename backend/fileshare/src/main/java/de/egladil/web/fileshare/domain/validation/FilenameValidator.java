// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.validation;

import java.util.Set;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * FilenameValidator
 */
public class FilenameValidator implements ConstraintValidator<ValidFilename, String> {

    // Deine Zeichenmenge (kein Slash/Backslash), plus mindestens 1 Zeichen
    private static final Pattern ALLOWED = Pattern.compile("^[a-zA-Z0-9_.][a-zA-Z0-9_.-]*$");

    // Windows-reservierte Gerätenamen (case-insensitive) – sonst knallt es auf
    // Windows
    private static final Set<String> WINDOWS_RESERVED = Set
            .of("CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9",
                    "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9");

    private int maxLength;

    @Override
    public void initialize(ValidFilename constraintAnnotation) {
        this.maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        if (value.length() > maxLength) {
            return false;
        }
        if (!ALLOWED.matcher(value).matches()) {
            return false;
        }
        if (value.equals(".")) {
            return false;
        }
        if (value.contains("..")) {
            return false;
        }

        String upper = value.toUpperCase();
        // Windows: "CON.txt" ist auch verboten (Reserved names gelten vor dem ersten
        // Punkt)
        String base = upper.split("\\.", 2)[0];
        if (WINDOWS_RESERVED.contains(base)) {
            return false;
        }

        return true;
    }

}

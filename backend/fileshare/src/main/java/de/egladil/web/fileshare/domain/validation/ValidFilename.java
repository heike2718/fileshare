// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = FilenameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFilename {

    String message() default "Ungültiger Dateiname " + ValidationPatternsAndMessages.INVALID_FILENAME_MESSAGE_DETAILS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Max. Länge (plattformneutral). 255 ist ein praktikabler Default.
     */
    int maxLength() default 255;
}

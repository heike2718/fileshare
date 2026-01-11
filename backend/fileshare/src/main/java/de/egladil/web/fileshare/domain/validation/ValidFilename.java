//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = FilenameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFilename {

  String message() default "Ungültiger Dateiname";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * Max. Länge (plattformneutral). 255 ist ein praktikabler Default.
   */
  int maxLength() default 255;

  /**
   * Falls du "."-Dateien grundsätzlich verbieten willst.
   */
  boolean allowLeadingDot() default false;
}

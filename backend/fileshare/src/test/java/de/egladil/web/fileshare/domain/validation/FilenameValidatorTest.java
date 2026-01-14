//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.egladil.web.fileshare.TestUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class FilenameValidatorTest {

  private static final String EXPECTED_MESSAGE = "Ungültiger Dateiname Erlaubt sind ASCII-Buchstaben, Ziffern und die Sonderzeichen . _ -. Minus darf nicht am Anfang stehen. Länge max = 255";

  private final Validator validator;

  public FilenameValidatorTest() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  class DefaultFilenameValidatorTestBean {

    @ValidFilename
    private final String field;

    public DefaultFilenameValidatorTestBean(String field) {
      this.field = field;
    }
  }

  class CustomizedFilenameValidatorTestBean {
    @ValidFilename(maxLength = 10)
    private final String field;

    public CustomizedFilenameValidatorTestBean(String field) {
      this.field = field;
    }
  }

  @Nested
  class DefaultAnnotationTests {

    @Test
    void should_pass_when_valid() {
      // arrange
      String name = TestUtils.generateStringWithLength("u", 255);
      DefaultFilenameValidatorTestBean bean = new DefaultFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<DefaultFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(0, violations.size());

    }

    @Test
    void should_pass_when_starts_with_dot() {
      // arrange
      String name = ".env";
      DefaultFilenameValidatorTestBean bean = new DefaultFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<DefaultFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(0, violations.size());

    }

    @Test
    void should_pass_when_starts_with_underscore() {
      // arrange
      String name = "_env";
      DefaultFilenameValidatorTestBean bean = new DefaultFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<DefaultFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(0, violations.size());
    }

    @Test
    void should_not_pass_when_too_long() {
      // arrange
      String name = TestUtils.generateStringWithLength("u", 256);
      DefaultFilenameValidatorTestBean bean = new DefaultFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<DefaultFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(1, violations.size());

      final ConstraintViolation<DefaultFilenameValidatorTestBean> violation = violations.iterator()
          .next();

      assertEquals(EXPECTED_MESSAGE, violation.getMessage());
    }

    @Test
    void should_not_pass_when_starts_with_minus() {
      // arrange
      String name = "-datei.txt";
      DefaultFilenameValidatorTestBean bean = new DefaultFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<DefaultFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(1, violations.size());

      final ConstraintViolation<DefaultFilenameValidatorTestBean> violation = violations.iterator()
          .next();

      assertEquals(EXPECTED_MESSAGE, violation.getMessage());

    }

    @Test
    void should_not_pass_when_only_one_dot() {
      // arrange
      String name = ".";
      DefaultFilenameValidatorTestBean bean = new DefaultFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<DefaultFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(1, violations.size());

      final ConstraintViolation<DefaultFilenameValidatorTestBean> violation = violations.iterator()
          .next();

      assertEquals(EXPECTED_MESSAGE, violation.getMessage());
    }

    @Test
    void should_not_pass_when_only_two_dots() {
      // arrange
      String name = "..";
      DefaultFilenameValidatorTestBean bean = new DefaultFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<DefaultFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(1, violations.size());

      final ConstraintViolation<DefaultFilenameValidatorTestBean> violation = violations.iterator()
          .next();

      assertEquals(EXPECTED_MESSAGE, violation.getMessage());
    }

    @Test
    void should_not_pass_when_contains_two_dots() {
      // arrange
      String name = "file..txt";
      DefaultFilenameValidatorTestBean bean = new DefaultFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<DefaultFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(1, violations.size());

      final ConstraintViolation<DefaultFilenameValidatorTestBean> violation = violations.iterator()
          .next();

      assertEquals(EXPECTED_MESSAGE, violation.getMessage());
    }
  }

  @Nested
  class CustimizedAnnotationTests {

    @Test
    void should_not_pass_when_too_long() {
      // arrange
      String name = TestUtils.generateStringWithLength("u", 11);
      CustomizedFilenameValidatorTestBean bean = new CustomizedFilenameValidatorTestBean(name);

      // act
      Set<ConstraintViolation<CustomizedFilenameValidatorTestBean>> violations = validator.validate(bean);

      // assert
      assertEquals(1, violations.size());

      final ConstraintViolation<CustomizedFilenameValidatorTestBean> violation = violations.iterator()
          .next();

      assertEquals(EXPECTED_MESSAGE, violation.getMessage());
    }
  }
}

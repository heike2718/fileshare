//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class FilenamePatternTest {

  @Test
  void fuzz_basic_multilingual_plane() {
    Pattern safe = Pattern.compile("^[A-Za-z0-9_.-]+$");

    List<String> mismatches = new ArrayList<>();

    for (int cp = 0; cp <= 0xFFFF; cp++) { // BMP
      if (!Character.isDefined(cp)) {
        continue;
      }

      final String ch = new String(Character.toChars(cp));

      boolean allowed = (cp >= 'A' && cp <= 'Z')
          || (cp >= 'a' && cp <= 'z')
          || (cp >= '0' && cp <= '9')
          || cp == '_' || cp == '.' || cp == '-';

      boolean matches = safe.matcher("a" + ch + "b").matches();

      if (allowed != matches) {
        mismatches.add(
            "U+%04X '%s' allowed=%s matches=%s".formatted(cp, printable(ch), allowed, matches));
      }
    }

    assertTrue(mismatches.isEmpty(),
        () -> "Whitelist mismatches (" + mismatches.size() + "):\n"
            + String.join("\n", mismatches.subList(0, Math.min(200, mismatches.size())))
            + (mismatches.size() > 200 ? "\n... (truncated)" : ""));

  }

  private static String printable(String ch) {
    // Steuerzeichen sichtbar machen
    int cp = ch.codePointAt(0);
    if (Character.isISOControl(cp) || Character.isWhitespace(cp) && !ch.equals(" ")) {
      return "\\u%04X".formatted(cp);
    }
    return ch;
  }

}

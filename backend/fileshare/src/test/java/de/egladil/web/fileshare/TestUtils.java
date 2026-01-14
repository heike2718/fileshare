// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare;

public class TestUtils {

    public static String generateStringWithLength(final String letterOrDidgit, final int length) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(letterOrDidgit);
        }

        return sb.toString();
    }

}

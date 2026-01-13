// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.auth.config;

import io.quarkus.runtime.annotations.StaticInitSafe;

import io.smallrye.config.ConfigMapping;

@StaticInitSafe
@ConfigMapping(prefix = "session-cookie")
public interface SessionCookieConfig {

    String name();

    String sameSite();

    boolean secure();

    String path();

    default String toLog() {

        return "SessionCookieConfig=[name=" + name() + ", path=" + path() + ", sameSite=" + sameSite() + ", secure="
                + secure() + "]";

    }
}

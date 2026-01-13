// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.core;

import io.quarkus.runtime.annotations.StaticInitSafe;

import io.smallrye.config.ConfigMapping;

@StaticInitSafe
@ConfigMapping(prefix = "fileshare")
public interface FileshareConfig {

    String uploadDir();

    long maxBytes();
}

//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.cdi;

import de.egladil.web.fileshare.domain.core.FileshareConfig;
import de.egladil.web.fileshare.domain.exceptions.FileshareRuntimeException;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class StartupListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

  /** The Quarkus root path. */
  @ConfigProperty(name = "quarkus.http.root-path")
  String quarkusRootPath;

  /** The Port. */
  @ConfigProperty(name = "quarkus.http.port")
  String port;

  /** The Cors allowed origins. */
  @ConfigProperty(name = "quarkus.http.cors.origins")
  String corsAllowedOrigins;

  /** The Target origin. */
  @ConfigProperty(name = "target.origin")
  String targetOrigin;

  @ConfigProperty(name = "quarkus.rest-client.filescanner.url")
  String filescannerUrl;

  @ConfigProperty(name = "quarkus.rest-client.authprovider.url")
  String authProviderUrl;

  @ConfigProperty(name = "auth-app.url")
  String authAppUrl;

  @ConfigProperty(name = "public-redirect-url")
  String loginRedirectUrl;

  @ConfigProperty(name = "session.idle.timeout.minutes")
  Integer sessionIdleTimeoutMinutes;

  /** The Version. */
  @ConfigProperty(name = "quarkus.application.version")
  String version;

  @Inject
  FileshareConfig fileshareUploadConfig;

  /**
   * On startup.
   *
   * @param startupEvent StartupEvent - the startupEvent
   */
  @SuppressWarnings("unused")
  void onStartup(@Observes final StartupEvent startupEvent) {

    final boolean exists = Files.exists(Path.of(fileshareUploadConfig.uploadDir()));

    if (!exists) {
      throw new FileshareRuntimeException("filesharepfad [" + fileshareUploadConfig.uploadDir() + "] existiert nicht. config-Property fileshare.upload.dir prüfen");
    }

    LOGGER
        .info(" ===========> Version {} of the application is starting with profiles {}", version,
            StringUtils.join(ConfigUtils.getProfiles()));

    LOGGER.info(" ===========>  session timeout nach {} min", sessionIdleTimeoutMinutes);
    LOGGER.info(" ===========>  quarkus.http.cors.origins={}", corsAllowedOrigins);
    LOGGER.info(" ===========>  targetOrigin={}", targetOrigin);
    LOGGER.info(" ===========>  quarkusRootPath={}", quarkusRootPath);
    LOGGER.info(" ===========>  authAppUrl={}", authAppUrl);
    LOGGER.info(" ===========>  authProviderUrl={}", authProviderUrl);
    LOGGER.info(" ===========>  loginRedirectUrl={}", loginRedirectUrl);
    LOGGER.info(" ===========>  filescannerUrl={}", filescannerUrl);
    LOGGER.info(" ===========>  uploadDir={}", fileshareUploadConfig.uploadDir());
    LOGGER.info(" ===========>  maxBytes={}", fileshareUploadConfig.maxBytes());
    LOGGER.info(" ===========>  port={}", port);
  }
}

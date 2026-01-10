//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.cdi;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
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

  @ConfigProperty(name = "quarkus.rest-client.authprovider.url")
  String authProviderUrl;

  @ConfigProperty(name = "auth-app.url")
  String authAppUrl;

  @ConfigProperty(name = "public-redirect-url")
  String loginRedirectUrl;

  @ConfigProperty(name = "session.idle.timeout.minutes", defaultValue = "120")
  int sessionIdleTimeoutMinutes = 120;

  /** The Version. */
  @ConfigProperty(name = "quarkus.application.version")
  String version;

  /**
   * On startup.
   *
   * @param startupEvent StartupEvent - the startupEvent
   */
  @SuppressWarnings("unused")
  void onStartup(@Observes final StartupEvent startupEvent) {

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
    LOGGER.info(" ===========>  port={}", port);
  }
}

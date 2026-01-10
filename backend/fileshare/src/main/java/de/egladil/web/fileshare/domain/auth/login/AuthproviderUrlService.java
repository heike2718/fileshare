//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.auth.login;

import de.egladil.web.fileshare.domain.auth.clientauth.ClientAccessTokenService;
import de.egladil.web.fileshare.domain.core.AppMessage;
import de.egladil.web.fileshare.domain.exceptions.ClientAuthException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class AuthproviderUrlService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthproviderUrlService.class);

  @ConfigProperty(name = "auth-app.url")
  String authAppUrl;

  @ConfigProperty(name = "public-redirect-url")
  String publicRedirectUrl;

  @Inject
  ClientAccessTokenService clientAccessTokenService;

  /**
   * Gibt die LoginUrl zurück.
   * @return AppMessage
   */
  public AppMessage getLoginUrl() {

    // hierher ausgelagert, damit ClientAccessTokenService testbar wird.
    String nonce = UUID.randomUUID().toString();
    String accessToken = clientAccessTokenService.orderAccessToken(nonce);

    if (StringUtils.isBlank(accessToken)) {
      LOGGER.error("Fehler beim Authentisieren des Clients: accessToken blank");

      throw new ClientAuthException("Fehler beim Authentisieren des Clients");
    }

    String redirectUrl = authAppUrl + "login?accessToken=" + accessToken + "&state=login&redirectUrl="
        + publicRedirectUrl;

    LOGGER.debug(redirectUrl);

    return AppMessage.builder().text(redirectUrl).type("info").build();
  }
}

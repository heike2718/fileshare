//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.auth.clientauth;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class OAuthClientCredentialsProvider {

  @ConfigProperty(name = "public-client-id")
  String publicClientId;

  @ConfigProperty(name = "public-client-secret")
  String publicClientSecret;

  /**
   * @param nonce String, darf manchmal null sein.
   * @return OAuthClientCredentials
   */
  public OAuthClientCredentials getClientCredentials(final String nonce) {
    return OAuthClientCredentials.create(publicClientId, publicClientSecret, nonce);
  }
}

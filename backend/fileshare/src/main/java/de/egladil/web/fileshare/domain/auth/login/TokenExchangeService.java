// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.auth.login;

import java.util.Map;
import java.util.UUID;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.fileshare.domain.auth.clientauth.OAuthClientCredentials;
import de.egladil.web.fileshare.domain.auth.dto.MessagePayload;
import de.egladil.web.fileshare.domain.auth.dto.ResponsePayload;
import de.egladil.web.fileshare.domain.exceptions.ClientAuthException;
import de.egladil.web.fileshare.infrastructure.restclient.AuthproviderRestClient;

@RequestScoped
public class TokenExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenExchangeService.class);

    @Inject
    @RestClient
    AuthproviderRestClient authproviderRestClient;

    /**
     * Tauscht das AccessToken (ist ein OTT).
     *
     * @param clientId     String
     * @param clientSecret String
     * @param oneTimeToken String
     * @return String
     */
    public String exchangeTheOneTimeToken(final String clientId, final String clientSecret, final String oneTimeToken) {

        final String nonce = UUID.randomUUID().toString();

        OAuthClientCredentials clientCredentials = OAuthClientCredentials.create(clientId, clientSecret, nonce);

        try (Response response = authproviderRestClient.exchangeOneTimeTokenWithJwt(oneTimeToken, clientCredentials);) {

            ResponsePayload responsePayload = response.readEntity(ResponsePayload.class);

            return this.checkNonceAndExtractTheJwt(nonce, responsePayload);
        }
    }

    private String checkNonceAndExtractTheJwt(final String expectedNonce, final ResponsePayload responsePayload) {

        MessagePayload messagePayload = responsePayload.getMessage();

        if (messagePayload.isOk()) {

            @SuppressWarnings("unchecked")
            Map<String, String> dataMap = (Map<String, String>) responsePayload.getData();
            String responseNonce = dataMap.get("nonce");

            if (!expectedNonce.equals(responseNonce)) {

                {

                    LOGGER.error("Security Thread: zurückgesendetes nonce stimmt nicht");
                    throw new ClientAuthException();
                }
            }

            return dataMap.get("jwt");
        } else {

            LOGGER
                    .error("Authentisierung des Clients hat nicht geklappt: {} - {}", messagePayload.getLevel(),
                            messagePayload.getMessage());
            throw new ClientAuthException();
        }
    }
}

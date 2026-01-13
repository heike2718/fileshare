// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.auth.clientauth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.fileshare.domain.auth.dto.ResponsePayload;
import de.egladil.web.fileshare.infrastructure.restclient.AuthproviderRestClient;

@ApplicationScoped
public class InitAccessTokenDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitAccessTokenDelegate.class);

    @Inject
    @RestClient
    AuthproviderRestClient authproviderRestClient;

    public ResponsePayload authenticateClient(final OAuthClientCredentials credentials) {

        try (Response authResponse = authproviderRestClient.authenticateClient(credentials);) {

            ResponsePayload responsePayload = authResponse.readEntity(ResponsePayload.class);

            return responsePayload;
        }
    }
}

//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.restclient;

import de.egladil.web.fileshare.domain.auth.clientauth.OAuthClientCredentials;
import de.egladil.web.fileshare.domain.exceptions.DownstreamServiceException;
import de.egladil.web.fileshare.domain.exceptions.FileshareRuntimeException;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.temporal.ChronoUnit;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "authprovider")
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthproviderRestClient {

  @POST
  @Path("clients/client/accesstoken")
  @Retry(maxRetries = 3, delay = 1000)
  @Timeout(value = 10, unit = ChronoUnit.SECONDS)
  Response authenticateClient(OAuthClientCredentials clientSecrets);

  @PUT
  @Path("token/exchange/{oneTimeToken}")
  @Retry(maxRetries = 3, delay = 1000)
  @Timeout(value = 10, unit = ChronoUnit.SECONDS)
  public Response exchangeOneTimeTokenWithJwt(@PathParam(value = "oneTimeToken") final String oneTimeToken,
      final OAuthClientCredentials clientCredentials);

  @ClientExceptionMapper
  @Blocking
  static RuntimeException map(Response response) {

    int status = response.getStatus();

    if (status < 400) {
      return null;
    }

    if (status == 400) {
      throw new FileshareRuntimeException("Antwort 400 - BAD_REQUEST vom authprovider. Implementierungsfehler?");
    }

    if (status == 401) {
      throw new DownstreamServiceException(
          "client-Authentifizierung fehlgeschlagen. Mal clientId hier und in der auth-database prüfen", RestClientType.filescanner);
    }

    return new DownstreamServiceException(
        "unerwarteter status " + status + ". Haben wir nicht mit gerechnet", RestClientType.filescanner);
  }
}

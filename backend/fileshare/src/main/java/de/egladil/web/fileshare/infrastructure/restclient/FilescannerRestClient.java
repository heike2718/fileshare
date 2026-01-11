//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.restclient;

import de.egladil.web.fileshare.domain.exceptions.FileshareRuntimeException;
import de.egladil.web.fileshare.domain.upload.FilescannerResponseDto;
import de.egladil.web.fileshare.domain.upload.ScanRequestDto;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "filescanner")
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface FilescannerRestClient {

  /**
   * Sendet das File an den filescanner zum Durchleuchten.
   * @param apiVersion
   * @param scanRequest
   * @return
   */
  @POST
  @Path("files/file")
  FilescannerResponseDto scanFile(@HeaderParam("API-Version") int apiVersion, ScanRequestDto scanRequest);

  @ClientExceptionMapper
  @Blocking
  static RuntimeException map(Response response) {

    int status = response.getStatus();

    if (status < 400) {
      return null;
    }

    if (status == 500) {
      throw new FileshareRuntimeException("status 500 vom filescanner. Mal dort ins log schauen.");
    }

    if (status == 401) {
      throw new FileshareRuntimeException(
          "status 401 vom filescanner. Mal clientId hier und im filescanner prüfen");
    }

    return new FileshareRuntimeException(
        "unerwarteter status " + status + " vom filescanner. Haben wir nicht mit gerechnet");
  }

}

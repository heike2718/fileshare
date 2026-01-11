//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.error;

import de.egladil.web.fileshare.domain.core.ErrorLevel;
import de.egladil.web.fileshare.domain.core.ErrorResponseDto;
import de.egladil.web.fileshare.domain.exceptions.InacceptablePayloadException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(1500)
public class InacceptablePayloadExceptionMapper implements ExceptionMapper<InacceptablePayloadException> {

  @Override
  public Response toResponse(InacceptablePayloadException exception) {
    final ErrorResponseDto responsePayload = ErrorResponseDto
        .builder()
        .errorLevel(ErrorLevel.ERROR)
        .message("Inputvalidierung fehlgeschlagen: " + exception.getMessage())
        .build();

    return Response.status(Response.Status.BAD_REQUEST).entity(responsePayload).build();
  }
}

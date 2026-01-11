//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.error;

import de.egladil.web.fileshare.domain.core.ErrorLevel;
import de.egladil.web.fileshare.domain.core.ErrorResponseDto;
import de.egladil.web.fileshare.domain.exceptions.SchadcodeException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(1500)
public class SchadcodeExceptionMapper implements ExceptionMapper<SchadcodeException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SchadcodeExceptionMapper.class);


  @Override
  public Response toResponse(SchadcodeException exception) {
    final ErrorResponseDto responsePayload = ErrorResponseDto
        .builder()
        .errorLevel(ErrorLevel.ERROR)
        .message("Inputvalidierung fehlgeschlagen: Datei enthält Schadcode!!!")
        .build();

    LOGGER.warn(exception.getMessage());

    return Response.status(Response.Status.BAD_REQUEST).entity(responsePayload).build();
  }
}

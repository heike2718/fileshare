//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.error;

import de.egladil.web.fileshare.domain.core.ErrorLevel;
import de.egladil.web.fileshare.domain.core.ErrorResponseDto;
import de.egladil.web.fileshare.domain.exceptions.DownstreamServiceException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(1500)
public class DownstreamServiceExceptionMapper implements ExceptionMapper<DownstreamServiceException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DownstreamServiceExceptionMapper.class);

  @Override
  public Response toResponse(DownstreamServiceException exception) {

    LOGGER.error(exception.getMessage());

    final ErrorResponseDto responsePayload = ErrorResponseDto
        .builder()
        .errorLevel(ErrorLevel.ERROR)
        .message(exception.getRestClientType() + " hat ein Problem.")
        .build();

    return Response.status(Status.BAD_GATEWAY).entity(responsePayload).build();
  }
}

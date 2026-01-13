// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.infrastructure.error;

import jakarta.annotation.Priority;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.fileshare.domain.core.ErrorLevel;
import de.egladil.web.fileshare.domain.core.ErrorResponseDto;

@Provider
@Priority(1500)
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessingExceptionMapper.class);

    @Override
    public Response toResponse(ProcessingException exception) {

        LOGGER.error("einer der backend-Dienste ist gerade nicht da: {}", exception.getMessage(), exception);

        final ErrorResponseDto responsePayload = ErrorResponseDto
                .builder()
                .errorLevel(ErrorLevel.ERROR)
                .message("Einer der backend-Dienste ist gerade nicht nicht erreichbar.")
                .build();

        return Response.status(Status.SERVICE_UNAVAILABLE).entity(responsePayload).build();
    }
}

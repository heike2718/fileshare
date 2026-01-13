// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.infrastructure.error;

import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import de.egladil.web.fileshare.domain.core.ErrorLevel;
import de.egladil.web.fileshare.domain.core.ErrorResponseDto;
import de.egladil.web.fileshare.domain.exceptions.FileExistsException;

@Provider
@Priority(1500)
public class FileExistsExceptionMapper implements ExceptionMapper<FileExistsException> {

    @Override
    public Response toResponse(FileExistsException exception) {
        final ErrorResponseDto payload = ErrorResponseDto
                .builder()
                .errorLevel(ErrorLevel.WARN)
                .message(exception.getMessage())
                .build();
        return Response.status(Status.PRECONDITION_FAILED).entity(payload).build();
    }
}

// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.infrastructure.error;

import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import de.egladil.web.fileshare.domain.core.ErrorLevel;
import de.egladil.web.fileshare.domain.core.ErrorResponseDto;
import de.egladil.web.fileshare.domain.exceptions.UnsupportedVersionException;

@Provider
@Priority(1500)
public class UnsupportedVersionExceptionMapper implements ExceptionMapper<UnsupportedVersionException> {

    @Override
    public Response toResponse(final UnsupportedVersionException versionException) {
        return Response
                .status(Response.Status.NOT_ACCEPTABLE)
                .entity(ErrorResponseDto
                        .builder()
                        .errorLevel(ErrorLevel.ERROR)
                        .message(versionException.getMessage())
                        .build())
                .build();
    }
}

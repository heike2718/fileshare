//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.error;

import de.egladil.web.fileshare.domain.exceptions.FileNotFoundException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(1500)
public class FileNotFoundExceptionMapper implements ExceptionMapper<FileNotFoundException> {

  @Override
  public Response toResponse(FileNotFoundException exception) {
    return Response.status(Status.NOT_FOUND).build();
  }
}

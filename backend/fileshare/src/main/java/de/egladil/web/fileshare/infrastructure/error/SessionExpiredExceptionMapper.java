//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.error;

import de.egladil.web.fileshare.domain.exceptions.SessionExpiredException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(1500)
public class SessionExpiredExceptionMapper implements ExceptionMapper<SessionExpiredException> {

  @Override
  public Response toResponse(SessionExpiredException exception) {
    return Response.status(440).build();
  }
}

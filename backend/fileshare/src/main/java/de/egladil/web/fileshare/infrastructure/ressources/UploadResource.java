//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.ressources;

import de.egladil.web.fileshare.domain.core.DateiDto;
import de.egladil.web.fileshare.domain.upload.FileUploadService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/upload")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")

public class UploadResource {

  @Inject
  FileUploadService uploadService;

  @POST
  public Response uploadFile(DateiDto datei) {
    uploadService.writeFile(datei);
    return Response.status(204).build();
  }
}

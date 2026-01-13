//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.ressources;

import de.egladil.web.fileshare.domain.core.FileDto;
import de.egladil.web.fileshare.domain.files.FileInfoDto;
import de.egladil.web.fileshare.domain.files.FileService;
import de.egladil.web.fileshare.domain.upload.FileUploadService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Path("api/files")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")

public class FileshareResource {

  @Inject
  FileUploadService uploadService;

  @Inject
  FileService fileService;

  @GET
  public List<FileInfoDto> listFiles() {
    return fileService.listFiles();
  }

  @POST
  public FileInfoDto uploadFile(FileDto datei) {
    final FileInfoDto fileInfoDto = uploadService.writeFile(datei);
    return fileInfoDto;
  }

  @DELETE
  @Path("{id}")
  public Response deleteFile(@PathParam(value = "id") String id) {

    this.fileService.deleteFile(id);
    return Response.status(Status.NO_CONTENT).build();
  }
 }

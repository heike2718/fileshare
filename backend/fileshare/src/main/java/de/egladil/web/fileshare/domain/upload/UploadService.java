// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.upload;

import de.egladil.web.fileshare.domain.core.FileDto;
import de.egladil.web.fileshare.domain.core.FileshareConfig;
import de.egladil.web.fileshare.domain.exceptions.FileExistsException;
import de.egladil.web.fileshare.domain.exceptions.FileshareRuntimeException;
import de.egladil.web.fileshare.domain.exceptions.InacceptablePayloadException;
import de.egladil.web.fileshare.domain.files.FileInfoDto;
import de.egladil.web.fileshare.domain.files.FileInfoStorage;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class UploadService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

  @Inject
  FileshareConfig fileshareConfig;

  @Inject
  UploadScannerDelegate uploadScannerDelegate;

  @Inject
  FileInfoStorage storage;

  /**
   * Wenn alles OK ist, wird die Datei ins Filesystem geschrieben.
   *
   * @param datei DateiDto
   * @return Path
   */
  public FileInfoDto writeFile(FileDto datei) {

    Optional<FileInfoDto> opt = storage.findByName(datei.getName());

    if (opt.isPresent()) {
      throw new FileExistsException("Eine Datei mit diesem Namen gibt es schon.");
    }

    uploadScannerDelegate.scanFile(datei);

    byte[] decodedBytes;
    try {
      decodedBytes = Base64.getDecoder().decode(datei.getDataBase64());
    } catch (IllegalArgumentException e) {
      LOGGER.error(e.getMessage(), e);
      throw new InacceptablePayloadException("Ungültige Base64-Daten");
    }

    Path target = Path.of(fileshareConfig.uploadDir()).resolve(datei.getName()).normalize();

    if (!target.startsWith(Path.of(fileshareConfig.uploadDir()))) {
      throw new FileshareRuntimeException("Ungültiger Zielpfad");
    }
    try {
      Files.createDirectories(target.getParent());
      Files.write(target, decodedBytes);

      FileInfoDto fileInfo = storage.fileAdded(target);
      return fileInfo;
    } catch (IOException e) {
      throw new FileshareRuntimeException("Datei konnte nicht geschrieben werden: " + target, e);
    }
  }
}

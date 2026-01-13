//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.upload;

import de.egladil.web.fileshare.domain.core.DateiDto;
import de.egladil.web.fileshare.domain.core.FileshareConfig;
import de.egladil.web.fileshare.domain.exceptions.FileshareRuntimeException;
import de.egladil.web.fileshare.domain.exceptions.InacceptablePayloadException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class FileUploadService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);

  @Inject
  FileshareConfig fileshareConfig;

  @Inject
  UploadScannerDelegate uploadScannerDelegate;

  /**
   * Wenn alles OK ist, wird die Datei ins Filesystem geschrieben.
   *
   * @param datei DateiDto
   */
  public void writeFile(DateiDto datei) {
    if (datei == null || datei.getName() == null || datei.getDataBase64() == null) {
      throw new InacceptablePayloadException("DateiDto unvollständig (name oder daten fehlen)");
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
    } catch (IOException e) {
      throw new FileshareRuntimeException("Datei konnte nicht geschrieben werden: " + target, e);
    }
  }
}

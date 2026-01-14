// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.download;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import de.egladil.web.fileshare.domain.core.FileDto;
import de.egladil.web.fileshare.domain.exceptions.FileNotFoundException;
import de.egladil.web.fileshare.domain.exceptions.FileshareRuntimeException;
import de.egladil.web.fileshare.domain.files.FileInfoDto;
import de.egladil.web.fileshare.domain.files.FileInfoStorage;

@ApplicationScoped
public class DownloadService {

    @Inject
    FileInfoStorage storage;

    /**
     * Holt die Datei zum Download ab.
     *
     * @param id String
     * @return FileDto
     */
    public FileDto readFile(String id) {

        final FileInfoDto fileInfoDto = storage.findById(id);

        if (fileInfoDto == null) {
            throw new FileNotFoundException();
        }

        Path path = fileInfoDto.getPath();

        try (InputStream in = Files.newInputStream(path);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                OutputStream b64 = Base64.getEncoder().wrap(out)) {

            in.transferTo(b64);
            b64.flush();

            String base64 = out.toString(StandardCharsets.US_ASCII);

            return FileDto.builder().name(fileInfoDto.getName()).dataBase64(base64).build();
        } catch (IOException e) {
            throw new FileshareRuntimeException("Konnte Datei nicht herunterladen: " + e.getMessage(), e);
        }
    }
}

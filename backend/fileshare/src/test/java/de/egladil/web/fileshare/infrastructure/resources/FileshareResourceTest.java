//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.resources;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.wildfly.common.Assert.assertFalse;

import de.egladil.web.fileshare.domain.core.ErrorLevel;
import de.egladil.web.fileshare.domain.core.ErrorResponseDto;
import de.egladil.web.fileshare.domain.core.FileDto;
import de.egladil.web.fileshare.domain.core.FileshareConfig;
import de.egladil.web.fileshare.domain.files.FileInfoDto;
import de.egladil.web.fileshare.profiles.FileshareResourceTestProfile;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(FileshareResourceTestProfile.class)
@TestHTTPEndpoint(FileshareResource.class)
@TestSecurity(user = "cc73be9f-4fea-43ff-bff3-588fd1dae843", roles = {"ADMIN", "LEHRER"})
public class FileshareResourceTest {

  @Inject
  FileshareConfig config;

  @BeforeEach
  void setup() throws IOException {
    Path testDir = Path.of(config.uploadDir());
    Files.list(testDir).forEach(file -> {
      if (!file.getFileName().startsWith(".")) {
        try {
          Files.delete(file);
        } catch (IOException e) {
          fail("kann Test nicht vorbereiten: " + e.getMessage());
        }
      }
    });
  }

  @Test
  void upload_should_not_fail_when_base64_is_slashes() {

    // arrange
    FileDto fileDto = FileDto.builder()
        .name("test.txt")
        .dataBase64("////")
        .build();

    // act
    final FileInfoDto fileInfoDto = given()
        .contentType("application/json")
        .accept("application/json")
        .body(fileDto)
        .post()
        .then()
        .statusCode(200)
        .and()
        .extract()
        .as(FileInfoDto.class);

    // assert
    assertAll(() -> assertEquals("test.txt", fileInfoDto.getName()),
        () -> assertNotNull(fileInfoDto.getId()),
        () -> assertNotNull(fileInfoDto.getUploadedAt()),
        () -> assertEquals("text/plain", fileInfoDto.getContentType()),
        () -> assertEquals("3 B", fileInfoDto.getSizeFormatted()));

  }

  @Test
  void should_list_upload_download_delete() {

    // arrange
    FileDto fileDto = FileDto.builder()
        .name("rundreise.txt")
        .dataBase64("RGFzIGlzdCBlaW4gdXBsb2FkLVRlc3QK")
        .build();

    // act 1
    FileInfoDto[] fileInfoDtos = given()
        .accept("application/json")
        .get()
        .then()
        .statusCode(200)
        .and()
        .extract()
        .as(FileInfoDto[].class);

    // assert 1
    assertEquals(0, fileInfoDtos.length);

    // act 2 upload
    final FileInfoDto erste = given()
        .contentType("application/json")
        .accept("application/json")
        .body(fileDto)
        .post()
        .then()
        .statusCode(200)
        .and()
        .extract()
        .as(FileInfoDto.class);

    String id = erste.getId();
    assertNotNull(id);

    final Path fileImFilesystem = Path.of(this.config.uploadDir(), erste.getName());
    assertAll(() -> assertTrue(Files.isReadable(fileImFilesystem)),
        () -> assertTrue(Files.isRegularFile(fileImFilesystem)));


    // act 3
    fileInfoDtos = given()
        .accept("application/json")
        .get()
        .then()
        .statusCode(200)
        .and()
        .extract()
        .as(FileInfoDto[].class);

    // assert 1
    assertEquals(1, fileInfoDtos.length);

    final FileInfoDto fileInfoDto = fileInfoDtos[0];

    assertAll(() -> assertEquals("rundreise.txt", fileInfoDto.getName()),
        () -> assertEquals(id, fileInfoDto.getId()));

    // act 4
    ErrorResponseDto errorResponseDto = given()
        .contentType("application/json")
        .accept("application/json")
        .body(fileDto)
        .post()
        .then()
        .statusCode(412)
        .and()
        .extract()
        .as(ErrorResponseDto.class);

    assertEquals("Eine Datei mit diesem Namen gibt es schon.", errorResponseDto.getMessage());
    assertEquals(ErrorLevel.WARN, errorResponseDto.getErrorLevel());

    // act 5 download
    final FileDto download =  given()
        .contentType("application/json")
        .accept("application/json")
        .get(id)
        .then()
        .statusCode(200)
        .and()
        .extract()
        .as(FileDto.class);

    assertAll(() -> assertEquals(fileDto.getName(), download.getName()),
        () -> assertEquals(fileDto.getDataBase64(), download.getDataBase64()));

    // act 6 delete
    given()
        .contentType("application/json")
        .accept("application/json")
        .delete(id)
        .then()
        .statusCode(204);

    assertFalse(Files.exists(fileImFilesystem));

    fileInfoDtos = given()
        .contentType("application/json")
        .accept("application/json")
        .get()
        .then()
        .statusCode(200)
        .and()
        .extract()
        .as(FileInfoDto[].class);

    // assert 1
    assertEquals(0, fileInfoDtos.length);
  }

  @Test
  void should_reject_virus() {

    // arrange
    FileDto fileDto = FileDto.builder()
        .name("virus.txt")
        .dataBase64("WDVPIVAlQEFQWzRcUFpYNTQoUF4pN0NDKTd9JEVJQ0FSLVNUQU5EQVJELUFOVElWSVJVUy1URVNULUZJTEUhJEgrSCo=")
        .build();

    // act
    final ErrorResponseDto errorResponseDto = given()
        .contentType("application/json")
        .accept("application/json")
        .body(fileDto)
        .post()
        .then()
        .statusCode(400)
        .and()
        .extract()
        .as(ErrorResponseDto.class);

    // assert
    assertAll(() -> assertEquals(ErrorLevel.ERROR, errorResponseDto.getErrorLevel()),
        () -> assertEquals("Inputvalidierung fehlgeschlagen: Datei enthält Schadcode!!!", errorResponseDto.getMessage()));
  }
}

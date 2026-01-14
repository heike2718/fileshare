//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.infrastructure.resources;

import static io.restassured.RestAssured.given;

import de.egladil.web.fileshare.domain.core.FileDto;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(FileshareResource.class)
@TestSecurity(user = "abcde", roles = {"STANDARD,LEHRER,PRIVAT,KL_ADMIN"})
public class FileshareResourceAuthorizationTest {

  @Test
  void should_listFiles_be_forbidden() {

    given()
        .accept("application/json")
        .get()
        .then()
        .statusCode(403);
  }

  @Test
  void should_upload_be_forbidden() {

    // arrange
    FileDto fileDto = FileDto.builder()
        .name("rundreise.txt")
        .dataBase64("RGFzIGlzdCBlaW4gdXBsb2FkLVRlc3QK")
        .build();

    // act
    given()
        .contentType("application/json")
        .accept("application/json")
        .body(fileDto)
        .post()
        .then()
        .statusCode(403);
  }

  @Test
  void should_download_be_forbidden() {

    // act
    given()
        .accept("application/json")
        .get("abcde")
        .then()
        .statusCode(403);
  }

  @Test
  void should_delete_be_forbidden() {

    // act
    given()
        .accept("application/json")
        .delete("abcde")
        .then()
        .statusCode(403);
  }

}

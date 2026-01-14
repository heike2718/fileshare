// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.infrastructure.resources;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;

import de.egladil.web.fileshare.domain.core.ErrorResponseDto;
import de.egladil.web.fileshare.domain.core.FileDto;
import de.egladil.web.fileshare.profiles.MaxBase64TestProfile;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestProfile(MaxBase64TestProfile.class)
@TestHTTPEndpoint(FileshareResource.class)
@TestSecurity(user = "cc73be9f-4fea-43ff-bff3-588fd1dae843", roles = { "ADMIN" })
public class FileshareResourceValidationTest {

    @Test
    void should_fail_when_file_to_large() {

        // arrange
        FileDto fileDto = FileDto.builder().name("test.txt").dataBase64("RGFzIGlzdCBlaW4gdXBsb2FkLVRlc3QK").build();

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
        assertEquals("Inputvalidierung fehlgeschlagen: Datei ist ungültig oder zu groß", errorResponseDto.getMessage());
    }

    @Test
    void should_fail_when_base64_is_invalid() {
        // arrange
        FileDto fileDto = FileDto.builder().name("test.txt").dataBase64("abc").build();

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
        assertEquals("Inputvalidierung fehlgeschlagen: Datei ist ungültig oder zu groß", errorResponseDto.getMessage());

    }

}

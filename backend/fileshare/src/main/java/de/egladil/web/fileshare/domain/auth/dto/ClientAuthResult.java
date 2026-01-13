// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@lombok.extern.jackson.Jacksonized
@Schema(name = "ClientAuthResult", description = "Ergebnis der Authentifizierung beim authprovider")
public class ClientAuthResult {

    @Schema(description = "Gültigkeit des generierten Einmaltokens")
    @JsonProperty
    private long expiresAt;

    @Schema(description = "ein Kontext, also Login oder SignUp")
    @JsonProperty
    private String state;

    @NotBlank
    @Schema(
            description = "für die Autorisierung generierter String, der vom authprovider unverändert zurückgegeben wird")
    @JsonProperty
    private String nonce;

    @NotBlank
    @Schema(description = "Einmaltoken, mit dem sich der authprovider-Client das JWT holen kann")
    @JsonProperty
    private String idToken;

}

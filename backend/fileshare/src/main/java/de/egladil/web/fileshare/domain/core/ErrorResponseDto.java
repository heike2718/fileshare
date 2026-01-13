// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.core;

import jakarta.validation.constraints.Pattern;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.egladil.web.fileshare.domain.validation.ValidationPatternsAndMessages;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@lombok.extern.jackson.Jacksonized
@Schema(name = "ErrorResponseDto", description = "ein Error-Objekt")
public class ErrorResponseDto {

    @JsonProperty
    @Schema(name = "errorLevel", examples = { "ERROR", "WARN" })
    ErrorLevel errorLevel;

    @JsonProperty
    @Pattern(regexp = ValidationPatternsAndMessages.INPUT_SECURED, message = "message enthält ungültige Zeichen")
    @Schema(name = "message", examples = { "Es ist ein Fehler aufgetreten" })
    String message;
}

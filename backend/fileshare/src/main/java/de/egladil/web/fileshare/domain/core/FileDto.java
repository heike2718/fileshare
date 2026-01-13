// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.egladil.web.fileshare.domain.validation.MaxBase64Size;
import de.egladil.web.fileshare.domain.validation.ValidFilename;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "eine Datei zum sharen")
@lombok.extern.jackson.Jacksonized
public class FileDto {

    @JsonProperty
    @Schema(description = "Dateiname", examples = "datei-zum-transportieren.zip", required = true)
    @NotBlank(message = "name ist erforderlich.")
    @ValidFilename(allowLeadingDot = true)
    private String name;

    @JsonProperty
    @Schema(description = "base64 encodierte Daten der Datei", required = true, example = "SGFsbG8gWnVzYW1tZW4=")
    @NotNull(message = "Daten der Datei dürfen nicht leer sein.")
    @MaxBase64Size()
    private String dataBase64;
}

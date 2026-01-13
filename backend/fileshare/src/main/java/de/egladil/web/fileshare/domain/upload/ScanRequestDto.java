// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.upload;

import jakarta.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.egladil.web.fileshare.domain.core.FileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload für den filescanner-Microservice")
@lombok.extern.jackson.Jacksonized
public class ScanRequestDto {

    @JsonProperty
    @NotNull
    private String clientId;

    @JsonProperty
    private String fileOwner;

    @JsonProperty
    @NotNull
    private FileDto upload;

}

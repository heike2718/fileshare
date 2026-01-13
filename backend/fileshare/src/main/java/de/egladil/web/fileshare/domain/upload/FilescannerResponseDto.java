// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.upload;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Antwort des filescanner-Microservices")
@lombok.extern.jackson.Jacksonized
public class FilescannerResponseDto {

    @JsonProperty
    private String userID;

    @JsonProperty
    private String uploadName;

    @JsonProperty
    private String mediaType;

    @JsonProperty
    private VirusDetection virusDetection;

    @JsonProperty
    private ThreadDetection threadDetection;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FilescannerResponseDto{");
        sb.append("userID='").append(userID).append('\'');
        sb.append(", uploadName='").append(uploadName).append('\'');
        sb.append(", virusDetection=").append(virusDetection);
        sb.append(", threadDetection=").append(threadDetection);
        sb.append('}');
        return sb.toString();
    }
}

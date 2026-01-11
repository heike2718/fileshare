//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.egladil.web.fileshare.domain.core.DateiDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

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
  private DateiDto upload;

}

//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ein Virus wurde entdeckt")
@lombok.extern.jackson.Jacksonized
public class VirusDetection {

  @JsonProperty
  private boolean virusDetected;

  @JsonProperty
  private String scannerMessage;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("VirusDetection{");
    sb.append("virusDetected=").append(virusDetected);
    sb.append(", scannerMessage='").append(scannerMessage).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

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
@Schema(description = "etwas Sicherheitsrelevantes anderes als ein Virus wurde entdeckt (Zip-Bombe oder so)")
@lombok.extern.jackson.Jacksonized
public class ThreadDetection {

  @JsonProperty
  private boolean securityThreadDetected;

  @JsonProperty
  private String securityCheckMessage;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ThreadDetection{");
    sb.append("securityThreadDetected=").append(securityThreadDetected);
    sb.append(", securityCheckMessage='").append(securityCheckMessage).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

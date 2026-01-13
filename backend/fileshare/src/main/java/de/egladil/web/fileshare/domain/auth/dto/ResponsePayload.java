// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.auth.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@lombok.extern.jackson.Jacksonized
@Schema(
        name = "ResponsePayload",
        description = "generisches Response-Objekt, das ein MessagePayload und ggf. Daten enthält")
public class ResponsePayload {

    @JsonProperty
    @Schema(description = "das MessagePayload")
    private MessagePayload message;

    @JsonProperty
    @Schema(description = "daten die als JSON mitgegeben werden. Kann null sein")
    private Object data;
}

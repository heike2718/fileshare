// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.auth.session;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
@Builder
@lombok.extern.jackson.Jacksonized
public class UserDto {

    @JsonProperty
    private String fullName; // claim full_name

    @JsonProperty
    private String[] roles; // groups -> roles

    @JsonProperty
    private boolean anonym;

}

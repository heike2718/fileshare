//=====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
//=====================================================

package de.egladil.web.fileshare.domain.exceptions;

import de.egladil.web.fileshare.infrastructure.restclient.RestClientType;

public class DownstreamServiceException extends RuntimeException {

  private final RestClientType restClientType;

  public DownstreamServiceException(String message, final RestClientType restClientType) {
    super(message);
    this.restClientType = restClientType;
  }

  public RestClientType getRestClientType() {
    return restClientType;
  }

}

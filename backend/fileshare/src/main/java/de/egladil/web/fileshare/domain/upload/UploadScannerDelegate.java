// =====================================================
// Projekt: fileshare
// (c) Heike Winkelvoß
// =====================================================

package de.egladil.web.fileshare.domain.upload;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.security.identity.SecurityIdentity;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import de.egladil.web.fileshare.domain.core.FileDto;
import de.egladil.web.fileshare.domain.exceptions.SchadcodeException;
import de.egladil.web.fileshare.infrastructure.restclient.FilescannerRestClient;

@ApplicationScoped
public class UploadScannerDelegate {

    @ConfigProperty(name = "public-client-id")
    String clientId;

    @Inject
    SecurityIdentity securityIdentity;

    @RestClient
    @Inject
    FilescannerRestClient fileScannerClient;

    /**
     * Kapselt den Aufruf des Filescanners-
     *
     * @param fileDto DateiDto
     * @throws SchadcodeException
     */
    public void scanFile(FileDto fileDto) throws SchadcodeException {

        String fileOwnerId = securityIdentity.getPrincipal().getName();

        ScanRequestDto scanRequestDto = ScanRequestDto
                .builder()
                .upload(fileDto)
                .fileOwner(fileOwnerId)
                .clientId(clientId)
                .build();

        final FilescannerResponseDto responseDto = fileScannerClient.scanFile(2, scanRequestDto);

        if (isSchadcode(responseDto)) {
            throw new SchadcodeException("hochgeladene Datei enthält Schadcode: " + responseDto);
        }
    }

    boolean isSchadcode(FilescannerResponseDto responseDto) {

        if (responseDto.getThreadDetection() == null && responseDto.getVirusDetection() == null) {
            return false;
        }

        return responseDto.getThreadDetection().isSecurityThreadDetected()
                || responseDto.getVirusDetection().isVirusDetected();
    }

}

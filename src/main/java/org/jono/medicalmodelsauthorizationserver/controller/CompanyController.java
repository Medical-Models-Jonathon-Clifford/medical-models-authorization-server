package org.jono.medicalmodelsauthorizationserver.controller;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.jono.medicalmodelsauthorizationserver.service.MmUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final MmUserInfoService mmUserInfoService;

    @Autowired
    public CompanyController(final MmUserInfoService mmUserInfoService) {
        this.mmUserInfoService = mmUserInfoService;
    }

    @GetMapping(value = "/pictures/company-logo-{companyId}.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable final String companyId) {
        try {
            final String base64Image = getBase64ImageFromStorage(companyId);
            final byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + companyId + "\"")
                    .body(imageBytes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String getBase64ImageFromStorage(final String companyId) {
        return mmUserInfoService.getBase64Logo(companyId);
    }
}

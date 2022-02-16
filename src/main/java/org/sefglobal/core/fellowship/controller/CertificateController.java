package org.sefglobal.core.fellowship.controller;

import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.fellowship.model.Certificate;
import org.sefglobal.core.fellowship.service.CertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fellowship/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @CrossOrigin(origins = "https://sefglobal.org")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Certificate getCertificateById(@PathVariable Integer id) throws ResourceNotFoundException{
        return certificateService.getCertificateById(id);
    }
}

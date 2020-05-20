package org.sefglobal.core.controller;

import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.model.Certificate;
import org.sefglobal.core.service.CertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fellowship/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Certificate> getCertificates(){
        return certificateService.getCertificates();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Certificate getCertificateById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException{
        return certificateService.getCertificateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Certificate addCertificate(@RequestBody Certificate certificate){
        return certificateService.addCertificate(certificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCertificateById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return certificateService.deleteCertificate(id);
    }
}

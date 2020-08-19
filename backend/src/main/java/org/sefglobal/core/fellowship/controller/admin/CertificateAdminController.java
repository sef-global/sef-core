package org.sefglobal.core.fellowship.controller.admin;

import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.fellowship.model.Certificate;
import org.sefglobal.core.fellowship.service.CertificateService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fellowship/admin/certificates")
public class CertificateAdminController {

    private final CertificateService certificateService;

    public CertificateAdminController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Certificate> getCertificates(@RequestParam int pageNumber, int pageSize){
        return certificateService.getCertificates(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Certificate addCertificate(@RequestBody Certificate certificate){
        return certificateService.addCertificate(certificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCertificateById(@PathVariable Integer id) throws ResourceNotFoundException {
        return certificateService.deleteCertificate(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Certificate updateCertificateById(@PathVariable Integer id,
                                             @RequestBody Certificate certificate)
            throws ResourceNotFoundException {
        return certificateService.updateCertificate(id, certificate);
    }
}

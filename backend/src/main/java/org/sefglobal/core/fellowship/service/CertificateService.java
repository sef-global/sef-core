package org.sefglobal.core.fellowship.service;

import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.fellowship.model.Certificate;
import org.sefglobal.core.fellowship.repository.CertificateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    /**
     * Retrieves all the certificates in the repository
     *
     * @param pageNumber which is the starting index of the page
     * @param pageSize   which is the size of the page
     * @return {@link Page} of {@link Certificate}
     */
    public Page<Certificate> getCertificates(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return certificateRepository.findAll(pageable);
    }

    /**
     * Retrieves the certificate with the requested id
     *
     * @param id  the id to be searched and retrieved
     * @return Certificate object
     * @throws ResourceNotFoundException if a certificate with the requested id doesn't exist in the repository
     */
    public Certificate getCertificateById(Integer id) throws ResourceNotFoundException{
        return certificateRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Couldn't find a certificate with id: "+ id));
    }

    /**
     * Adds a new certificate to the repository
     *
     * @param certificate  the certificate to be added to the repository
     * @return The saved certificate to the repository
     */
    public Certificate addCertificate(Certificate certificate){
        return certificateRepository.save(certificate);
    }

    /**
     * Deletes the certificate with the requested id
     *
     * @param id  the id to be searched and deleted
     * @return A String stating id of the deleted certificate
     * @throws ResourceNotFoundException if a certificate doesn't exist with the requested id
     */
    public String deleteCertificate(Integer id) throws ResourceNotFoundException {
        Certificate certificate = certificateRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Couldn't find a certificate with id: "+ id));
        certificateRepository.delete(certificate);
        return "Certificate deleted - Id :" + id;
    }
}

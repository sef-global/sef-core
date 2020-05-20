package org.sefglobal.core.service;

import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.model.Certificate;
import org.sefglobal.core.repository.CertificateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    /**
     * Retrieves all the certificates in the repository
     *
     * @return List of certificate objects
     */
    public List<Certificate> getCertificates(){
        return certificateRepository.findAll();
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

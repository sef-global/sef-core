package org.sefglobal.core.fellowship.repository;

import org.sefglobal.core.fellowship.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
}

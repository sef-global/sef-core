package org.sefglobal.core.repository;

import org.sefglobal.core.model.Ambassador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbassadorRepository extends JpaRepository<Ambassador, Long> {
}

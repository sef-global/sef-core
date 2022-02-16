package org.sefglobal.core.repository;

import org.sefglobal.core.model.Ambassador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbassadorRepository extends JpaRepository<Ambassador, Long> {
    Ambassador findByIdAndStatus(long id, String status);
    List<Ambassador> findAllByStatus(String status);
}

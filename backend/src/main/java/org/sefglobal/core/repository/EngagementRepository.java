package org.sefglobal.core.repository;

import org.sefglobal.core.model.Engagement;
import org.sefglobal.core.model.identity.EngagementIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement, EngagementIdentity> {
}

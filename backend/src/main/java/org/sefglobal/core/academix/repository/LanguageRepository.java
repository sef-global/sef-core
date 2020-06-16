package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for CRUD operations of {@link Language}
 */
public interface LanguageRepository extends JpaRepository<Language, Long> {

    boolean existsByLocale(String localeIdentifier);

    Optional<Language> getByLocale(String localeIdentifier);

}

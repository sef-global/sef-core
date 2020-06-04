package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.Language;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository for CRUD operations of {@link Language}
 */
public interface LanguageRepository extends PagingAndSortingRepository<Language, Long> {

    boolean existsByLocale(String localeIdentifier);

}

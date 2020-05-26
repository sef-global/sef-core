package org.sefglobal.core.repository;

import org.sefglobal.core.model.Language;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository for CRUD operations of {@link Language}
 */
public interface LanguageRepository extends PagingAndSortingRepository<Language, Long> {

    boolean existsByLocale(String localeIdentifier);

}

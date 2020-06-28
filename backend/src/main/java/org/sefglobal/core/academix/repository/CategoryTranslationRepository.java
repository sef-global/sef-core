package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.CategoryTranslation;
import org.sefglobal.core.academix.model.identifiers.CategoryTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTranslationRepository
        extends JpaRepository<CategoryTranslation, CategoryTranslationId> {
}

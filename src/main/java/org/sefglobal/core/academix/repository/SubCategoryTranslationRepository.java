package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.SubCategoryTranslation;
import org.sefglobal.core.academix.model.identifiers.SubCategoryTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryTranslationRepository
        extends JpaRepository<SubCategoryTranslation, SubCategoryTranslationId> {
}

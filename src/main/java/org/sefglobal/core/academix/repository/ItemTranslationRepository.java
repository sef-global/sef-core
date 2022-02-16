package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.ItemTranslation;
import org.sefglobal.core.academix.model.identifiers.ItemTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTranslationRepository
        extends JpaRepository<ItemTranslation, ItemTranslationId> {
}

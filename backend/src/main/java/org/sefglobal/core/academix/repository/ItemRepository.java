package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.projections.CustomItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    CustomItem getOneById(Long id);
    Page<CustomItem> getAllBySubCategories(SubCategory subCategory, Pageable pageable);
}

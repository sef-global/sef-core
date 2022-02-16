package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.model.projection.CustomItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<CustomItem> getAllBySubCategories(SubCategory subCategory, Pageable pageable);
    List<Item> getAllBySubCategories(SubCategory subCategory);
}

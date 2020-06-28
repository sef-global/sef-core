package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.projections.CustomItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // TODO: 6/28/20 Remove method 
    CustomItem getOneById(Long id);

    // TODO: 6/27/20 Remove method
    Page<CustomItem> getAllBySubCategories(SubCategory subCategory, Pageable pageable);

    Page<Item> getAllBySubCategories(Pageable pageable, SubCategory subCategory);
}

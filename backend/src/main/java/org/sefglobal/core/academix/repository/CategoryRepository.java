package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.projections.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<CustomCategory> findAllBy();
    Optional<CustomCategory> findCategoryById(Long categoryId);
}

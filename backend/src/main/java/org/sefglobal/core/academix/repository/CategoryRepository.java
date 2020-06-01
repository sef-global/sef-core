package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.projections.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<CustomCategory> findAllBy();
}

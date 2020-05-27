package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.projections.CustomSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<CustomSubCategory> getAllByCategory_Id(Long categoryId);
}

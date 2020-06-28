package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.projections.CustomSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    // TODO: 6/28/20 Remove method
    List<CustomSubCategory> getAllBy();

    // TODO: 6/28/20 Remove method
    List<CustomSubCategory> getAllByCategory_Id(Long categoryId);

    List<SubCategory> findAllByCategoryId(long category);

    // TODO: 6/28/20 Remove method
    Optional<CustomSubCategory> findSubCategoryById(Long subCategoryId);
}

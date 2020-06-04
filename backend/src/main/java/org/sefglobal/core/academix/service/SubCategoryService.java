package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.projections.CustomSubCategory;
import org.sefglobal.core.academix.repository.CategoryRepository;
import org.sefglobal.core.academix.repository.SubCategoryRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to manege {@link SubCategory}
 */
@Service
public class SubCategoryService {

    private final static Logger log = LoggerFactory.getLogger(SubCategoryService.class);
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all the subcategories by the requested category
     *
     * @param categoryId which is the id of the requested category
     * @return {@link List} of {@link SubCategory}
     * @throws ResourceNotFoundException if a category with the requested id doesn't exist
     */
    public List<CustomSubCategory> getAllSubCategoryByCategory(Long categoryId) throws ResourceNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            String msg = "Error, Category by id:" + categoryId + " doesn't exist";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return subCategoryRepository.getAllByCategory_Id(categoryId);
    }

    /**
     * Retrieves the subcategory with the requested id
     *
     * @param subCategoryId which is the id of the requested subCategory
     * @return CustomSubCategory object
     * @throws ResourceNotFoundException if a subcategory with the requested id doesn't exist
     */
    public CustomSubCategory getSubCategoryById(Long subCategoryId) throws ResourceNotFoundException {
        Optional<CustomSubCategory> subCategory = subCategoryRepository.findSubCategoryById(subCategoryId);
        if (!subCategory.isPresent()) {
            String msg = "Error, Sub Category by id:" + subCategoryId + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return subCategory.get();
    }
}

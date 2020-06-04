package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.projections.CustomCategory;
import org.sefglobal.core.academix.repository.CategoryRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to manage {@link Category}
 */
@Service
public class CategoryService {

    private final static Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all the categories
     *
     * @return {@link List} of {@link Category}
     */
    public List<CustomCategory> getAllCategories(){
        return categoryRepository.findAllBy();
    }

    /**
     * Retrieves the category with the requested id
     *
     * @param categoryId which is the id of the requested category
     * @return CustomCategory object
     * @throws ResourceNotFoundException if a category with the requested id doesn't exist
     */
    public CustomCategory getCategoryById(Long categoryId) throws ResourceNotFoundException {
        Optional<CustomCategory> category = categoryRepository.findBy_Id(categoryId);
        if (!category.isPresent()) {
            String msg = "Error, Category by id:" + categoryId + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return category.get();
    }
}

package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.projections.CustomCategory;
import org.sefglobal.core.academix.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to manage {@link Category}
 */
@Service
public class CategoryService {

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
}

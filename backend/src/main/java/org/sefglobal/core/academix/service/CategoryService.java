package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.model.CategoryTranslation;
import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.repository.CategoryRepository;
import org.sefglobal.core.academix.repository.ItemRepository;
import org.sefglobal.core.academix.repository.SubCategoryRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final static Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    public final ItemRepository itemRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           SubCategoryRepository subCategoryRepository,
                           ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Retrieves all the {@link Category} objects
     *
     * @return {@link List} of {@link Category} objects
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves the {@link Category} filtered from {@code id}
     *
     * @param id which is the id of the filtering {@link Category}
     * @return {@link Category}
     *
     * @throws ResourceNotFoundException if the requesting {@link Category} doesn't exist
     */
    public Category getCategoryById(long id) throws ResourceNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            String msg = "Error, Category by id: " + id + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return category.get();
    }

    /**
     * Retrieves all the {@link SubCategory} objects filtered from {@link Category} {@code id}
     *
     * @param id which is the Category id of the filtering {@link SubCategory} objects
     * @return {@link List} of {@link SubCategory} objects
     *
     * @throws ResourceNotFoundException if the requesting {@link Category} to filter {@link
     *                                   SubCategory} objects doesn't exist
     */
    public List<SubCategory> getSubCategoriesByCategoryId(long id)
            throws ResourceNotFoundException {
        if (!categoryRepository.existsById(id)) {
            String msg = "Error, Category by id: " + id + " doesn't exist";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return subCategoryRepository.findAllByCategoryId(id);
    }

    /**
     * Create new {@link Category}
     *
     * @param category which holds the data to be added
     * @return the created {@link Category}
     */
    public Category addCategory(Category category) {
        category.getTranslations()
                .forEach(categoryTranslation -> categoryTranslation.setCategory(category));
        return categoryRepository.save(category);
    }

    /**
     * Update a {@link Category} either by adding a new {@link CategoryTranslation} or by editing an
     * existing one
     *
     * @param id       which is the {@link Category} to be updated
     * @param category which is the updated data
     * @return the updated {@link Category}
     *
     * @throws ResourceNotFoundException is thrown if the requesting {@link Category} doesn't exist
     */
    public Category updateCategory(long id, Category category) throws ResourceNotFoundException {
        if (!categoryRepository.existsById(id)) {
            String msg = "Error, Category with id: " + id + " cannot be updated. " +
                    "Category doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        category.setId(id);
        category.getTranslations()
                .forEach(categoryTranslation -> categoryTranslation.setCategory(category));
        return categoryRepository.save(category);
    }

    /**
     * Delete a existing {@link Category}
     *
     * @param id which is the identifier of the {@link Category}
     * @return {@code true} if {@link Category} gets deleted
     *
     * @throws ResourceNotFoundException if {@link Category} for {@code id} doesn't exist
     */
    public boolean deleteCategory(long id) throws ResourceNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            String msg = "Error, Category with id: " + id + " cannot be deleted. " +
                         "Category doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        List<SubCategory> subCategories = optionalCategory.get().getSubCategories();
        for (SubCategory subCategory : subCategories) {
            List<Item> items = itemRepository.getAllBySubCategories(subCategory);
            subCategory.removeItems(items);
        }
        categoryRepository.deleteById(id);
        return true;
    }
}

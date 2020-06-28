package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.dto.CategoryDto;
import org.sefglobal.core.academix.dto.TranslationDto;
import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.model.CategoryTranslation;
import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.model.identifiers.CategoryTranslationId;
import org.sefglobal.core.academix.projections.CustomCategory;
import org.sefglobal.core.academix.repository.CategoryRepository;
import org.sefglobal.core.academix.repository.CategoryTranslationRepository;
import org.sefglobal.core.academix.repository.LanguageRepository;
import org.sefglobal.core.academix.repository.SubCategoryRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Service to manage {@link Category}
 */
@Service
public class CategoryService {

    private final static Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;
    private final CategoryTranslationRepository categoryTranslationRepository;
    private final SubCategoryRepository subCategoryRepository;
    public final LanguageRepository languageRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           CategoryTranslationRepository categoryTranslationRepository,
                           SubCategoryRepository subCategoryRepository,
                           LanguageRepository languageRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryTranslationRepository = categoryTranslationRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieves all the categories
     *
     * @return {@link List} of {@link Category}
     */
    // TODO: 6/25/20 Remove method
    public List<CustomCategory> getAllCategories() {
        return categoryRepository.findAllBy();
    }

    /**
     * Retrieves the category with the requested id
     *
     * @param categoryId which is the id of the requested category
     * @return CustomCategory object
     *
     * @throws ResourceNotFoundException if a category with the requested id doesn't exist
     */
    // TODO: 6/25/20 Remove method
    public CustomCategory getCategoryById(Long categoryId) throws ResourceNotFoundException {
        Optional<CustomCategory> category = categoryRepository.findCategoryById(categoryId);
        if (!category.isPresent()) {
            String msg = "Error, Category by id:" + categoryId + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return category.get();
    }

    /**
     * Add a new {@link Category}
     *
     * @param categoryDto which holds the data to be added
     * @return the created {@link CategoryDto}
     *
     * @throws ResourceNotFoundException if {@link Language} locale for a {@link CategoryTranslation}
     *                                   not found
     */
    // TODO: 6/25/20 Remove method
    public CategoryDto addCategory(CategoryDto categoryDto) throws ResourceNotFoundException {
        Category category = convertToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        categoryDto.setId(savedCategory.getId());
        return categoryDto;
    }

    /**
     * Update a existing {@link Category}'s {@link CategoryTranslation} data
     *
     * @param id          which is the identifier of the {@link Category}
     * @param isAllReset  which if true will reset all the existing translations for the {@link
     *                    Category}
     * @param categoryDto which holds the data to be added
     * @return {@code true} if {@link Category} gets updated
     *
     * @throws ResourceNotFoundException if {@link Language} locale for a {@link CategoryTranslation}
     *                                   not found
     */
    // TODO: 6/25/20 Remove method
    public boolean updateTranslations(long id, boolean isAllReset, CategoryDto categoryDto)
            throws ResourceNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            String msg = "Error, Category with id: " + id + " cannot be updated." +
                         " Category doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        Category existingCategory = categoryOptional.get();
        List<CategoryTranslation> existingTranslations = existingCategory.getTranslations();
        Category updatedCategory = convertToEntity(categoryDto);
        updatedCategory.setId(existingCategory.getId());
        List<CategoryTranslation> updatedTranslations = updatedCategory.getTranslations();

        if (isAllReset) {
            existingTranslations.clear();
            existingTranslations.addAll(updatedTranslations);
            categoryRepository.save(existingCategory);
            return true;
        }
        existingTranslations.addAll(updatedTranslations);
        try {
            categoryRepository.save(existingCategory);
            return true;
        } catch (Exception e) {
            String msg = "Error, Category with id: " + id + " cannot be updated. " +
                         "Category data already exists or invalid.";
            log.error(msg, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    /**
     * Retrieves all the {@link Category} objects
     *
     * @return {@link List} of {@link Category} objects
     */
    public List<Category> getAllCategoriesNew() {
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
    public Category getCategoryByIdNew(long id) throws ResourceNotFoundException {
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
    public List<SubCategory> getSubCategoriesByCategoryIdNew(long id)
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
        return categoryRepository.save(category);
    }

    /**
     * Update a {@link Category} either by adding a new {@link CategoryTranslation} or by editing an
     * existing one
     *
     * @param id       which is the {@link Category} to be updated
     * @param category which is the updated data
     * @return {@code true} if {@link Category} gets updated
     *
     * @throws ResourceNotFoundException is thrown if the requesting {@link Category} doesn't exist
     */
    public boolean updateCategory(long id, Category category) throws ResourceNotFoundException {
        boolean isUpdated = categoryRepository
                .findById(id)
                .map(updatableCategory -> {
                    category.getTranslations().forEach(updatedTranslation -> {
                        categoryTranslationRepository
                                .findById(new CategoryTranslationId(updatableCategory, updatedTranslation.getLanguage()))
                                .ifPresent(updatableTranslation ->
                                                   updatableTranslation.setName(updatedTranslation.getName()));
                        updatableCategory.addTranslation(updatedTranslation);
                    });
                    return categoryRepository.save(updatableCategory);
                })
                .isPresent();
        if (!isUpdated) {
            String msg = "Error, Category with id: " + id + " cannot be updated." +
                         " Category doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return true;
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
        if (!categoryRepository.existsById(id)) {
            String msg = "Error, Category with id: " + id + " cannot be deleted. " +
                         "Category doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        categoryRepository.deleteById(id);
        return true;
    }

    /**
     * Map a {@link CategoryDto} to {@link Category}
     *
     * @param categoryDto which is the objected to be mapped
     * @return mapped object
     *
     * @throws ResourceNotFoundException if {@link Language} locale for a {@link CategoryTranslation}
     *                                   not found
     */
    // TODO: 6/25/20 Remove method
    private Category convertToEntity(CategoryDto categoryDto) throws ResourceNotFoundException {
        Category category = new Category();
        for (TranslationDto translationDto : categoryDto.getTranslations()) {
            String locale = translationDto.getLanguage();
            Optional<Language> language = languageRepository.getByLocale(locale);
            if (!language.isPresent()) {
                String msg = "Error, Language unable for locale identifier: " + locale;
                log.error(msg);
                throw new ResourceNotFoundException(msg);
            }
            CategoryTranslation translation = new CategoryTranslation();
            translation.setLanguage(language.get());
            translation.setName(translationDto.getName());
            category.addTranslation(translation);
        }
        return category;
    }
}

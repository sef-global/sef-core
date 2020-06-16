package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.dto.CategoryDto;
import org.sefglobal.core.academix.dto.TranslationDto;
import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.model.CategoryTranslation;
import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.academix.projections.CustomCategory;
import org.sefglobal.core.academix.repository.CategoryRepository;
import org.sefglobal.core.academix.repository.LanguageRepository;
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
    public final LanguageRepository languageRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           LanguageRepository languageRepository) {
        this.categoryRepository = categoryRepository;
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieves all the categories
     *
     * @return {@link List} of {@link Category}
     */
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
    public CategoryDto addCategory(CategoryDto categoryDto) throws ResourceNotFoundException {
        Category category = convertToEntity(categoryDto);
        categoryRepository.save(category);
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

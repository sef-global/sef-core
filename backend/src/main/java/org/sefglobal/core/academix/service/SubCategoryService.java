package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.dto.SubCategoryDto;
import org.sefglobal.core.academix.dto.TranslationDto;
import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.model.SubCategoryTranslation;
import org.sefglobal.core.academix.model.identifiers.SubCategoryTranslationId;
import org.sefglobal.core.academix.projections.CustomSubCategory;
import org.sefglobal.core.academix.repository.CategoryRepository;
import org.sefglobal.core.academix.repository.ItemRepository;
import org.sefglobal.core.academix.repository.LanguageRepository;
import org.sefglobal.core.academix.repository.SubCategoryRepository;
import org.sefglobal.core.academix.repository.SubCategoryTranslationRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final SubCategoryTranslationRepository subCategoryTranslationRepository;
    private final ItemRepository itemRepository;
    public final LanguageRepository languageRepository;

    public SubCategoryService(CategoryRepository categoryRepository,
                              SubCategoryRepository subCategoryRepository,
                              SubCategoryTranslationRepository subCategoryTranslationRepository,
                              ItemRepository itemRepository,
                              LanguageRepository languageRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryTranslationRepository = subCategoryTranslationRepository;
        this.itemRepository = itemRepository;
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieves all the subcategories
     *
     * @return {@link List} of {@link SubCategory}
     */
    // TODO: 6/28/20 Remove method
    public List<CustomSubCategory> getSubcategories() {
        return subCategoryRepository.getAllBy();
    }

    /**
     * Retrieves all the subcategories by the requested category
     *
     * @param categoryId which is the id of the requested category
     * @return {@link List} of {@link SubCategory}
     * @throws ResourceNotFoundException if a category with the requested id doesn't exist
     */
    // TODO: 6/26/20 Remove method
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
    // TODO: 6/26/20 Remove method
    public CustomSubCategory getSubCategoryById(Long subCategoryId) throws ResourceNotFoundException {
        Optional<CustomSubCategory> subCategory = subCategoryRepository.findSubCategoryById(subCategoryId);
        if (!subCategory.isPresent()) {
            String msg = "Error, Sub Category by id:" + subCategoryId + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return subCategory.get();
    }

    /**
     * Add a new {@link SubCategory}
     *
     * @param categoryId which is the parent {@link Category} for {@link SubCategory}
     * @param subCategoryDto which holds the data to be added
     * @return the created {@link SubCategoryDto}
     *
     * @throws ResourceNotFoundException if {@link Category} for {@code categoryId} is not found ||
     *                                   if {@link Language} locale for a {@link SubCategoryTranslation}
     *                                   not found
     */
    // TODO: 6/26/20 Remove method
    public SubCategoryDto addSubCategory(long categoryId, SubCategoryDto subCategoryDto)
            throws ResourceNotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()){
            String msg = "Error, Category with id: " + categoryId + " doesn't exist. " +
                         "SubCategory's parent Category is invalid.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        SubCategory subCategory = convertToEntity(subCategoryDto);
        subCategory.setCategory(category.get());
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        subCategoryDto.setId(savedSubCategory.getId());
        return subCategoryDto;
    }

    /**
     * Update a existing {@link SubCategory}'s {@link SubCategoryTranslation} data
     *
     * @param id             which is the identifier of the {@link SubCategory}
     * @param isAllReset     which if true will reset all the existing translations for the {@link
     *                       SubCategory}
     * @param subCategoryDto which holds the data to be added
     * @return {@code true} if {@link SubCategory} gets updated
     *
     * @throws ResourceNotFoundException if {@link Language} locale for a {@link SubCategoryTranslation}
     *                                   not found
     */
    // TODO: 6/27/20 Remove method
    public boolean updateTranslation(long id, boolean isAllReset, SubCategoryDto subCategoryDto)
            throws ResourceNotFoundException {
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
        if (!subCategoryOptional.isPresent()) {
            String msg = "Error, SubCategory with id: " + id + " cannot be updated. " +
                         "SubCategory doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        SubCategory existingSubCategory = subCategoryOptional.get();
        List<SubCategoryTranslation> existingTranslations = existingSubCategory.getTranslations();
        SubCategory updatedSubCategory = convertToEntity(subCategoryDto);
        updatedSubCategory.setId(existingSubCategory.getId());
        List<SubCategoryTranslation> updatedTranslations = updatedSubCategory.getTranslations();

        if (isAllReset) {
            existingTranslations.clear();
            existingTranslations.addAll(updatedTranslations);
            subCategoryRepository.save(existingSubCategory);
            return true;
        }
        existingTranslations.addAll(updatedTranslations);
        try {
            subCategoryRepository.save(existingSubCategory);
            return true;
        } catch (Exception e) {
            String msg = "Error, SubCategory with id: " + id + " cannot be updated. " +
                         "SubCategory data already exists or invalid.";
            log.error(msg, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    /**
     * Retrieves all the subcategories
     *
     * @return {@link List} of {@link SubCategory}
     */
    public List<SubCategory> getAllSubcategories() {
        return subCategoryRepository.findAll();
    }

    /**
     * Retrieves the {@link SubCategory} filtered from {@code id}
     *
     * @param id which is the id of the filtering {@link SubCategory}
     * @return {@link SubCategory}
     *
     * @throws ResourceNotFoundException if the requesting {@link SubCategory} doesn't exist
     */
    public SubCategory getSubCategoryByIdNew(long id) throws ResourceNotFoundException {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if (!subCategory.isPresent()) {
            String msg = "Error, SubCategory by id: " + id + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return subCategory.get();
    }

    /**
     * Retrieves all the {@link Item} objects filtered from {@link SubCategory} {@code id}
     *
     * @param id         which is the SubCategory id of the filtering {@link Item} objects
     * @param pageNumber which is the starting index of the page
     * @param pageSize   which is the size of the page
     * @return {@link Page<Item>}
     *
     * @throws ResourceNotFoundException if the requesting {@link SubCategory} to filter {@link
     *                                   Item} objects doesn't exist
     */
    public Page<Item> getItemsBySubCategoryIdNew(long id, int pageNumber, int pageSize)
            throws ResourceNotFoundException {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if (!subCategory.isPresent()) {
            String msg = "Error, SubCategory by id: " + id + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return itemRepository.getAllBySubCategories(pageable, subCategory.get());
    }

    /**
     * Add a new {@link SubCategory}
     *
     * @param categoryId  which is the parent {@link Category} for {@link SubCategory}
     * @param subCategory which holds the data to be added
     * @return the created {@link SubCategory}
     *
     * @throws ResourceNotFoundException is thrown if the requesting {@link SubCategory} doesn't
     *                                   exist
     */
    public SubCategory addSubCategory(long categoryId, SubCategory subCategory)
            throws ResourceNotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            String msg = "Error, Category with id: " + categoryId + " doesn't exist. " +
                         "SubCategory's parent Category is invalid.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        subCategory.setCategory(category.get());
        return subCategoryRepository.save(subCategory);
    }

    /**
     * Update a {@link SubCategory} either by adding a new {@link SubCategoryTranslation} or by
     * editing an existing one
     *
     * @param id          which is the {@link SubCategory} to be updated
     * @param subCategory which is the updated data
     * @return {@code true} if {@link SubCategory} gets updated
     *
     * @throws ResourceNotFoundException is thrown if the requesting {@link Category} not found
     */
    public boolean updateSubCategory(long id, SubCategory subCategory)
            throws ResourceNotFoundException {
        boolean isUpdated = subCategoryRepository
                .findById(id)
                .map(updatableSubCategory -> {
                    subCategory.getTranslations().forEach(updatedTranslation -> {
                        subCategoryTranslationRepository
                                .findById(new SubCategoryTranslationId(updatableSubCategory, updatedTranslation.getLanguage()))
                                .ifPresent(updatableTranslation ->
                                                   updatableTranslation.setName(updatedTranslation.getName()));
                        updatableSubCategory.addTranslation(updatedTranslation);
                    });
                    return subCategoryRepository.save(updatableSubCategory);
                })
                .isPresent();
        if (!isUpdated) {
            String msg = "Error, SubCategory with id: " + id + " cannot be updated. SubCategory " +
                         "doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return true;
    }

    /**
     * Delete a existing {@link SubCategory}
     *
     * @param id which is the identifier of the {@link SubCategory}
     * @return {@code true} if {@link SubCategory} gets deleted
     *
     * @throws ResourceNotFoundException if {@link SubCategory} for {@code id} doesn't exist
     */
    public boolean deleteSubCategory(long id) throws ResourceNotFoundException {
        if (!subCategoryRepository.existsById(id)) {
            String msg = "Error, SubCategory with id: " + id + " cannot be deleted." +
                         " SubCategory doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        subCategoryRepository.deleteById(id);
        return true;
    }

    /**
     * Map a {@link SubCategoryDto} to {@link SubCategory}
     *
     * @param subCategoryDto which is the objected to be mapped
     * @return mapped object
     *
     * @throws ResourceNotFoundException if {@link Language} locale for a {@link SubCategoryTranslation}
     *                                   not found
     */
    // TODO: 6/26/20 Remove method
    private SubCategory convertToEntity(SubCategoryDto subCategoryDto)
            throws ResourceNotFoundException {
        SubCategory subCategory = new SubCategory();
        for (TranslationDto translationDto : subCategoryDto.getTranslations()) {
            String locale = translationDto.getLanguage();
            Optional<Language> language = languageRepository.getByLocale(locale);
            if (!language.isPresent()) {
                String msg = "Error, Language unable for locale identifier: " + locale;
                log.error(msg);
                throw new ResourceNotFoundException(msg);
            }
            SubCategoryTranslation translation = new SubCategoryTranslation();
            translation.setLanguage(language.get());
            translation.setName(translationDto.getName());
            subCategory.addTranslation(translation);
        }
        return subCategory;
    }
}

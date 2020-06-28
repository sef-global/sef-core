package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.dto.ItemDto;
import org.sefglobal.core.academix.dto.ItemTranslationDto;
import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.model.ItemTranslation;
import org.sefglobal.core.academix.model.identifiers.ItemTranslationId;
import org.sefglobal.core.academix.projections.CustomItem;
import org.sefglobal.core.academix.repository.ItemRepository;
import org.sefglobal.core.academix.repository.ItemTranslationRepository;
import org.sefglobal.core.academix.repository.LanguageRepository;
import org.sefglobal.core.academix.repository.SubCategoryRepository;
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
 * Service to manage {@link Item}
 */
@Service
public class ItemService {

    private final static Logger log = LoggerFactory.getLogger(ItemService.class);
    private final SubCategoryRepository subCategoryRepository;
    private final ItemRepository itemRepository;
    private final ItemTranslationRepository itemTranslationRepository;
    public final LanguageRepository languageRepository;

    public ItemService(SubCategoryRepository subCategoryRepository,
                       ItemRepository itemRepository,
                       ItemTranslationRepository itemTranslationRepository,
                       LanguageRepository languageRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.itemRepository = itemRepository;
        this.itemTranslationRepository = itemTranslationRepository;
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieves an item by the requested id
     *
     * @param id which is the id of the requested item
     * @return {@link Item}
     * @throws ResourceNotFoundException if an item with the requested id doesn't exist
     */
    // TODO: 6/28/20 Remove method
    public CustomItem getItemByID(Long id) throws ResourceNotFoundException{
        if (!itemRepository.existsById(id)){
            String msg = "Error, Item by id:" + id + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return itemRepository.getOneById(id);
    }

    /**
     * Retrieves all the items by the requested subcategory
     *
     * @param subCategoryId which is the id of the requested subcategory
     * @param pageNumber    which is the starting index of the page
     * @param pageSize      which is the size of the page
     * @return {@link Page} of {@link Item}
     * @throws ResourceNotFoundException if a subcategory with the requested id doesn't exist
     */
    // TODO: 6/27/20 Remove method
    public Page<CustomItem> getAllItemsBySubCategory(Long subCategoryId, int pageNumber, int pageSize) throws ResourceNotFoundException {
        if(!subCategoryRepository.existsById(subCategoryId)){
            String msg = "Error, Sub Category by id:" + subCategoryId + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        SubCategory subCategory = subCategoryRepository.getOne(subCategoryId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return itemRepository.getAllBySubCategories(subCategory, pageable);
    }

    /**
     * Add a new {@link Item}
     *
     * @param subCategoryIds which is the parent {@link SubCategory}s for {@link Item}
     * @param itemDto        which holds the data to be added
     * @return the created {@link ItemDto}
     *
     * @throws ResourceNotFoundException if {@link Language} locale for a {@link ItemTranslation}
     *                                   not found
     */
    // TODO: 6/27/20 Remove method
    public ItemDto addItem(List<Long> subCategoryIds, ItemDto itemDto)
            throws ResourceNotFoundException {
        List<SubCategory> existingSubCategories = subCategoryRepository.findAllById(subCategoryIds);
        if (subCategoryIds.size() > existingSubCategories.size()) {
            String msg = "Error, SubCategories are invalid. One or more SubCategories doesn't " +
                         "exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        Item item = convertToEntity(itemDto);
        existingSubCategories.forEach(item::addSubCategory);
        Item itemSaved = itemRepository.save(item);
        itemDto.setId(itemSaved.getId());
        return itemDto;
    }

    /**
     * Update a existing {@link Item}'s {@link ItemTranslation} data
     *
     * @param id         which is the identifier of the {@link Item}
     * @param isAllReset which if true will reset all the existing translations for the {@link
     *                   Item}
     * @param itemDto    which holds the data to be added
     * @return {@code true} if {@link Item} gets updated
     *
     * @throws ResourceNotFoundException if {@link Language} locale for a {@link ItemTranslation}
     *                                   not found
     */
    // TODO: 6/27/20 Remove method
    public boolean updateTranslation(long id, boolean isAllReset, ItemDto itemDto)
            throws ResourceNotFoundException {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (!itemOptional.isPresent()) {
            String msg = "Error, Item with id: " + id + " cannot be updated. " +
                         "Item doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        Item existingItem = itemOptional.get();
        List<ItemTranslation> existingTranslations = existingItem.getTranslations();
        Item updatedItem = convertToEntity(itemDto);
        updatedItem.setId(existingItem.getId());
        List<ItemTranslation> updatedTranslations = updatedItem.getTranslations();

        if (isAllReset) {
            existingTranslations.clear();
            existingTranslations.addAll(updatedTranslations);
            itemRepository.save(existingItem);
            return true;
        }
        existingTranslations.addAll(updatedTranslations);
        try {
            itemRepository.save(existingItem);
            return true;
        } catch (Exception e) {
            String msg = "Error, Item with id: " + id + " cannot be updated. " +
                         "Item data already exists or invalid.";
            log.error(msg, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    /**
     * Retrieves an item by the requested id
     *
     * @param id which is the id of the requested item
     * @return {@link Item}
     *
     * @throws ResourceNotFoundException if an item with the requested id doesn't exist
     */
    public Item getItemById(long id) throws ResourceNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            String msg = "Error, Item by id: " + id + " doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return item.get();
    }

    /**
     * Create a new {@link Item}
     *
     * @param subCategoryIds which are the parent {@link SubCategory}s of {@link Item}
     * @param item           which holds the data to be added
     * @return the created {@link Item}
     *
     * @throws ResourceNotFoundException is thrown if the requesting {@link Item} doesn't exist
     */
    public Item addItem(List<Long> subCategoryIds, Item item) throws ResourceNotFoundException {
        List<SubCategory> existingSubCategories = subCategoryRepository.findAllById(subCategoryIds);
        if (subCategoryIds.size() > existingSubCategories.size()) {
            String msg = "Error, SubCategories are invalid. One or more SubCategories doesn't " +
                         "exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }

        existingSubCategories.forEach(item::addSubCategory);
        return itemRepository.save(item);
    }

    /**
     * Update a {@link Item} either by adding a new {@link ItemTranslation} or by editing an
     * existing one
     *
     * @param id   which is the {@link Item} to be updated
     * @param item which holds the the updated data
     * @return {@code true} if {@link Item} gets updated
     *
     * @throws ResourceNotFoundException is thrown if the requesting {@link Item} doesn't exist
     */
    public boolean updateItem(long id, Item item)
            throws ResourceNotFoundException {
        boolean isUpdated = itemRepository
                .findById(id)
                .map(updatableItem -> {
                    item.getTranslations().forEach(updatedTranslation -> {
                        itemTranslationRepository
                                .findById(new ItemTranslationId(updatableItem, updatedTranslation.getLanguage()))
                                .ifPresent(updatableTranslation ->
                                                   updatableTranslation.setName(updatedTranslation.getName()));
                        updatableItem.addTranslation(updatedTranslation);
                    });
                    return itemRepository.save(updatableItem);
                })
                .isPresent();
        if (!isUpdated) {
            String msg = "Error, Item with id: " + id + " cannot be updated. Item doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return true;
    }

    /**
     * Delete a existing {@link Item}
     *
     * @param id which is the identifier of the {@link Item}
     * @return {@code true} if {@link Item} gets deleted
     *
     * @throws ResourceNotFoundException if {@link Item} for {@code id} doesn't exist
     */
    public boolean deleteItem(long id) throws ResourceNotFoundException {
        if (!itemRepository.existsById(id)) {
            String msg = "Error, Item with id: " + id + " cannot be deleted." +
                         " Item doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        itemRepository.deleteById(id);
        return true;
    }

    /**
     * Map a {@link ItemDto} to {@link Item}
     *
     * @param itemDto which is the objected to be mapped
     * @return mapped object
     *
     * @throws ResourceNotFoundException if {@link Language} locale for a {@link ItemTranslation}
     *                                   not found
     */
    // TODO: 6/27/20 Remove method
    private Item convertToEntity(ItemDto itemDto)
            throws ResourceNotFoundException {
        Item item = new Item();
        for (ItemTranslationDto translationDto : itemDto.getTranslations()) {
            String locale = translationDto.getLanguage();
            Optional<Language> language = languageRepository.getByLocale(locale);
            if (!language.isPresent()) {
                String msg = "Error, Language unable for locale identifier: " + locale;
                log.error(msg);
                throw new ResourceNotFoundException(msg);
            }
            ItemTranslation translation = new ItemTranslation();
            translation.setLanguage(language.get());
            translation.setName(translationDto.getName());
            translation.setDescription(translationDto.getDescription());
            item.addTranslation(translation);
        }
        item.setLink(itemDto.getLink());
        return item;
    }
}

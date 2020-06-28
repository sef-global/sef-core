package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.model.ItemTranslation;
import org.sefglobal.core.academix.model.identifiers.ItemTranslationId;
import org.sefglobal.core.academix.repository.ItemRepository;
import org.sefglobal.core.academix.repository.ItemTranslationRepository;
import org.sefglobal.core.academix.repository.LanguageRepository;
import org.sefglobal.core.academix.repository.SubCategoryRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}

package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.model.ItemTranslation;
import org.sefglobal.core.academix.repository.ItemRepository;
import org.sefglobal.core.academix.repository.SubCategoryRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final static Logger log = LoggerFactory.getLogger(ItemService.class);
    private final SubCategoryRepository subCategoryRepository;
    private final ItemRepository itemRepository;

    public ItemService(SubCategoryRepository subCategoryRepository,
                       ItemRepository itemRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.itemRepository = itemRepository;
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
     * @param item           which holds the data to be added
     * @return the created {@link Item}
     *
     * @throws ResourceNotFoundException is thrown if the requesting {@link Item} doesn't exist
     */
    public Item addItem(Item item) throws ResourceNotFoundException {
        List<Long> subCategoryIds = new ArrayList<>();
        List<SubCategory> subCategories = item.getSubCategories();
        for (SubCategory subCategory: subCategories){
            subCategoryIds.add(subCategory.getId());
        }
        List<SubCategory> existingSubCategories = subCategoryRepository.findAllById(subCategoryIds);
        if (subCategoryIds.size() > existingSubCategories.size()) {
            String msg = "Error, SubCategories are invalid. One or more SubCategories doesn't " +
                         "exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        item.setSubCategories(existingSubCategories);
        item.getTranslations().forEach(itemTranslation -> itemTranslation.setItem(item));
        return itemRepository.save(item);
    }

    /**
     * Update a {@link Item} either by adding a new {@link ItemTranslation} or by editing an
     * existing one
     *
     * @param id   which is the {@link Item} to be updated
     * @param item which holds the the updated data
     * @return the updated {@link Item}
     *
     * @throws ResourceNotFoundException is thrown if the requesting {@link Item} doesn't exist
     * @throws ResourceNotFoundException is thrown if the requesting {@link SubCategory} doesn't
     * exist
     */
    public Item updateItem(long id, Item item)
            throws ResourceNotFoundException {
        if (!itemRepository.existsById(id)) {
            String msg = "Error, Item with id: " + id + " cannot be updated. Item " +
                    "doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        item.setId(id);
        List<Long> subCategoryIds = new ArrayList<>();
        List<SubCategory> subCategories = item.getSubCategories();
        for (SubCategory subCategory : subCategories) {
            subCategoryIds.add(subCategory.getId());
        }
        List<SubCategory> existingSubCategories = subCategoryRepository.findAllById(subCategoryIds);
        if (subCategoryIds.size() > existingSubCategories.size()) {
            String msg =
                    "Error, SubCategories are invalid. One or more SubCategories doesn't " +
                            "exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        item.setSubCategories(existingSubCategories);
        item.getTranslations().forEach(itemTranslation -> itemTranslation.setItem(item));
        return itemRepository.save(item);
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

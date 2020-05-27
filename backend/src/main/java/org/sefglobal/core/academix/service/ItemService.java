package org.sefglobal.core.academix.service;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.projections.CustomItem;
import org.sefglobal.core.academix.repository.ItemRepository;
import org.sefglobal.core.academix.repository.SubCategoryRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service to manage {@link Item}
 */
@Service
public class ItemService {

    private final static Logger log = LoggerFactory.getLogger(ItemService.class);
    private final SubCategoryRepository subCategoryRepository;
    private final ItemRepository itemRepository;

    public ItemService(SubCategoryRepository subCategoryRepository, ItemRepository itemRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.itemRepository = itemRepository;
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
    public Page<CustomItem> getAllItemsBySubCategory(Long subCategoryId, int pageNumber, int pageSize) throws ResourceNotFoundException {
        if(!subCategoryRepository.existsById(subCategoryId)){
            String msg = "Error, Sub Category by id:"+subCategoryId+" doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        SubCategory subCategory = subCategoryRepository.getOne(subCategoryId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return itemRepository.getAllBySubCategories(subCategory, pageable);
    }
}

package org.sefglobal.core.academix.controller;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.projections.CustomItem;
import org.sefglobal.core.academix.service.ItemService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // TODO: 6/28/20 Remove method
    @GetMapping("/academix/sub-categories/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    public Page<CustomItem> getAllItemsBySubCategoryId(@PathVariable Long id,
                                                       @RequestParam int pageNumber,
                                                       @RequestParam int pageSize) throws ResourceNotFoundException {
        return itemService.getAllItemsBySubCategory(id, pageNumber, pageSize);
    }

    // TODO: 6/28/20 Remove method
    @GetMapping("/academix/items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomItem getItemsById(@PathVariable Long id) throws ResourceNotFoundException {
        return itemService.getItemByID(id);
    }

    @GetMapping("/academix/items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Item getItemById(@PathVariable long id) throws ResourceNotFoundException {
        return itemService.getItemById(id);
    }
}

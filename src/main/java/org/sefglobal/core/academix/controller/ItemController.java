package org.sefglobal.core.academix.controller;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.service.ItemService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academix/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Item getItemById(@PathVariable long id) throws ResourceNotFoundException {
        return itemService.getItemById(id);
    }
}

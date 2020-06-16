package org.sefglobal.core.academix.controller.admin;

import org.sefglobal.core.academix.dto.ItemDto;
import org.sefglobal.core.academix.service.ItemService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/academix/admin/items")
public class ItemAdminController {
    private final ItemService itemService;

    public ItemAdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addItem(@RequestParam List<Long> subCategoryIds,
                           @Valid @RequestBody ItemDto itemDto)
            throws ResourceNotFoundException {
        return itemService.addItem(subCategoryIds, itemDto);
    }

    @PutMapping("/{id}/translations")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean updateTranslation(@PathVariable long id,
                                     @RequestParam boolean isAllReset,
                                     @Valid @RequestBody ItemDto itemDto)
            throws ResourceNotFoundException {
        return itemService.updateTranslation(id, isAllReset, itemDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteItem(@PathVariable long id) throws ResourceNotFoundException {
        return itemService.deleteItem(id);
    }
}

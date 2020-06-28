package org.sefglobal.core.academix.controller;

import org.hibernate.validator.constraints.Range;
import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.service.SubCategoryService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academix/sub-categories")
@Validated
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubCategory> getAllSubCategories() {
        return subCategoryService.getAllSubcategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubCategory getSubCategoryById(@PathVariable long id)
            throws ResourceNotFoundException {
        return subCategoryService.getSubCategoryById(id);
    }

    @GetMapping("/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    public Page<Item> getItemsBySubCategoryId(@PathVariable long id,
                                              @RequestParam
                                                @Range int pageNumber,
                                              @RequestParam
                                                @Range(min = 1, max = 20) int pageSize)
            throws ResourceNotFoundException {
        return subCategoryService.getItemsBySubCategoryId(id, pageNumber, pageSize);
    }
}

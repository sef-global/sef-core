package org.sefglobal.core.academix.controller;

import org.hibernate.validator.constraints.Range;
import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.projections.CustomSubCategory;
import org.sefglobal.core.academix.service.SubCategoryService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    // TODO: 6/28/20 Remove
    @GetMapping("/academix/sub-categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomSubCategory> getSubCategories() {
        return subCategoryService.getSubcategories();
    }

    // TODO: 6/26/20 Remove method
    @GetMapping("/academix/categories/{id}/sub-categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomSubCategory> getAllSubCategoriesByCategoryId(@PathVariable Long id) throws ResourceNotFoundException {
        return subCategoryService.getAllSubCategoryByCategory(id);
    }

    // TODO: 6/26/20 Remove method
    @GetMapping("/academix/sub-categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomSubCategory getSubCategoryById(@PathVariable Long id) throws ResourceNotFoundException {
        return subCategoryService.getSubCategoryById(id);
    }

    @GetMapping("/academix/sub-categories/new")
    @ResponseStatus(HttpStatus.OK)
    public List<SubCategory> getAllSubCategoriesNew() {
        return subCategoryService.getAllSubcategories();
    }

    @GetMapping("/academix/sub-categories/{id}/new")
    @ResponseStatus(HttpStatus.OK)
    public SubCategory getSubCategoryByIdNew(@PathVariable long id)
            throws ResourceNotFoundException {
        return subCategoryService.getSubCategoryByIdNew(id);
    }

    @GetMapping("/academix/sub-categories/{id}/items/new")
    @ResponseStatus(HttpStatus.OK)
    public Page<Item> getItemsBySubCategoryId(@PathVariable long id,
                                              @RequestParam
                                                @Range int pageNumber,
                                              @RequestParam
                                                @Range(min = 1, max = 20) int pageSize)
            throws ResourceNotFoundException {
        return subCategoryService.getItemsBySubCategoryIdNew(id, pageNumber, pageSize);
    }
}

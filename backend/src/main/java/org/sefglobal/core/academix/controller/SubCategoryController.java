package org.sefglobal.core.academix.controller;

import org.sefglobal.core.academix.projections.CustomSubCategory;
import org.sefglobal.core.academix.service.SubCategoryService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping("/academix/categories/{id}/sub-categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomSubCategory> getAllSubCategoriesByCategoryId(@PathVariable Long id) throws ResourceNotFoundException {
        return subCategoryService.getAllSubCategoryByCategory(id);
    }
}

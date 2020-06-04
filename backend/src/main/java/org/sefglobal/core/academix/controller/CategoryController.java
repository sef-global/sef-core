package org.sefglobal.core.academix.controller;

import org.sefglobal.core.academix.projections.CustomCategory;
import org.sefglobal.core.academix.service.CategoryService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/academix/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomCategory> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/academix/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomCategory getCategoryById(@PathVariable Long id) throws ResourceNotFoundException {
        return categoryService.getCategoryById(id);
    }
}

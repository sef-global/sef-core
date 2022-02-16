package org.sefglobal.core.academix.controller;

import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.service.CategoryService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/academix/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category getCategoryById(@PathVariable long id) throws ResourceNotFoundException {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{id}/sub-categories")
    @ResponseStatus(HttpStatus.OK)
    public List<SubCategory> getSubCategoriesByCategoryId(@PathVariable long id)
            throws ResourceNotFoundException {
        return categoryService.getSubCategoriesByCategoryId(id);
    }
}

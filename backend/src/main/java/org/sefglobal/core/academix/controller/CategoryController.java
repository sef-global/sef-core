package org.sefglobal.core.academix.controller;

import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.projections.CustomCategory;
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

    // TODO: 6/25/20 Remove method
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomCategory> getAllCategories(){
        return categoryService.getAllCategories();
    }

    // TODO: 6/25/20 Remove method
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomCategory getCategoryById(@PathVariable Long id) throws ResourceNotFoundException {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategoriesNew() {
        return categoryService.getAllCategoriesNew();
    }

    @GetMapping("/{id}/new")
    @ResponseStatus(HttpStatus.OK)
    public Category getCategoryByIdNew(@PathVariable long id) throws ResourceNotFoundException {
        return categoryService.getCategoryByIdNew(id);
    }

    @GetMapping("/{id}/sub-categories/new")
    @ResponseStatus(HttpStatus.OK)
    public List<SubCategory> getSubCategoriesByCategoryIdNew(@PathVariable long id)
            throws ResourceNotFoundException {
        return categoryService.getSubCategoriesByCategoryIdNew(id);
    }
}

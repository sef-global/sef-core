package org.sefglobal.core.academix.controller.admin;

import org.sefglobal.core.academix.dto.CategoryDto;
import org.sefglobal.core.academix.service.CategoryService;
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

@RestController
@RequestMapping("/academix/admin/categories")
public class CategoryAdminController {
    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody CategoryDto category)
            throws ResourceNotFoundException {
        return categoryService.addCategory(category);
    }

    @PutMapping("/{id}/translations")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean updateTranslations(@PathVariable long id,
                                      @RequestParam boolean isAllReset,
                                      @Valid @RequestBody CategoryDto category)
            throws ResourceNotFoundException {
        return categoryService.updateTranslations(id, isAllReset, category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteCategory(@PathVariable long id) throws ResourceNotFoundException {
        return categoryService.deleteCategory(id);
    }
}

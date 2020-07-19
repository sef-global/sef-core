package org.sefglobal.core.academix.controller.admin;

import org.sefglobal.core.academix.model.SubCategory;
import org.sefglobal.core.academix.service.SubCategoryService;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/academix/admin/sub-categories")
public class SubCategoryAdminController {
    private final SubCategoryService subCategoryService;

    public SubCategoryAdminController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubCategory addSubCategory(@Valid @RequestBody SubCategory subCategory)
            throws ResourceNotFoundException {
        return subCategoryService.addSubCategory(subCategory);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubCategory updateSubCategory(@PathVariable long id,
                                     @Valid @RequestBody SubCategory subCategory)
            throws ResourceNotFoundException {
        return subCategoryService.updateSubCategory(id, subCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteSubCategory(@PathVariable long id) throws ResourceNotFoundException {
        return subCategoryService.deleteSubCategory(id);
    }
}

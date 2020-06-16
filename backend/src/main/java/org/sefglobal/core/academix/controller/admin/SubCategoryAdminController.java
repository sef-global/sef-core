package org.sefglobal.core.academix.controller.admin;

import org.sefglobal.core.academix.dto.SubCategoryDto;
import org.sefglobal.core.academix.service.SubCategoryService;
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
@RequestMapping("/academix/admin/sub-categories")
public class SubCategoryAdminController {
    private final SubCategoryService subCategoryService;

    public SubCategoryAdminController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SubCategoryDto addSubCategory(@PathVariable long categoryId,
                                  @Valid @RequestBody SubCategoryDto subCategory)
            throws ResourceNotFoundException {
        return subCategoryService.addSubCategory(categoryId, subCategory);
    }

    @PutMapping("/{id}/translations")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean updateTranslation(@PathVariable long id,
                                     @RequestParam boolean isAllReset,
                                     @Valid @RequestBody SubCategoryDto subCategory)
            throws ResourceNotFoundException {
        return subCategoryService.updateTranslation(id, isAllReset, subCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteSubCategory(@PathVariable long id) throws ResourceNotFoundException {
        return subCategoryService.deleteSubCategory(id);
    }
}

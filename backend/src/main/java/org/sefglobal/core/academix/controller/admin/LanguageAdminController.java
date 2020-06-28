package org.sefglobal.core.academix.controller.admin;

import org.sefglobal.core.academix.service.LanguageService;
import org.sefglobal.core.exception.BadRequestException;
import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academix/admin/languages")
public class LanguageAdminController {

    private final LanguageService languageService;

    public LanguageAdminController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Language addLanguage(@RequestParam String localeIdentifier) throws BadRequestException {
        return languageService.addLanguage(localeIdentifier);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteLanguage(@PathVariable long id) throws ResourceNotFoundException {
        return languageService.deleteLanguage(id);
    }
}

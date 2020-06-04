package org.sefglobal.core.academix.controller;

import org.hibernate.validator.constraints.Range;
import org.sefglobal.core.academix.service.LanguageService;
import org.sefglobal.core.exception.BadRequestException;
import org.sefglobal.core.academix.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/academix/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Language> getAllLanguages(@RequestParam @Range int pageNumber,
            @RequestParam @Range(min = 1, max = 20) int size) {
        return languageService.getAllLanguages(pageNumber, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addLanguage(@RequestParam String localeIdentifier) throws BadRequestException {
        languageService.addLanguage(localeIdentifier);
        return true;
    }
}

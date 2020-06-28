package org.sefglobal.core.academix.controller;

import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.academix.service.LanguageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/academix/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Language> getAllLanguages() {
        return languageService.getAllLanguages();
    }
}

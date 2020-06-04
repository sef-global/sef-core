package org.sefglobal.core.academix.service;

import org.sefglobal.core.exception.BadRequestException;
import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.academix.repository.LanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service to manage {@link Language} locales
 */
@Service
public class LanguageService {

    private static final Logger log = LoggerFactory.getLogger(LanguageService.class);
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Get all supported language locales
     *
     * @param pageNumber which is the starting index of the page
     * @param size       which is the size of the page
     * @return {@link Page} of {@link Language}
     */
    public Page<Language> getAllLanguages(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return languageRepository.findAll(pageable);
    }

    /**
     * Add a new {@link Language} support
     *
     * @param localeIdentifier which is the LCID
     * @throws BadRequestException if validating the {@code localeIdentifier} fails
     */
    public void addLanguage(String localeIdentifier) throws BadRequestException {
        if (localeIdentifier == null || localeIdentifier.isEmpty()) {
            String msg = "Error, LCID: " + localeIdentifier + " is invalid.";
            log.error(msg);
            throw new BadRequestException(msg);
        }
        if (languageRepository.existsByLocale(localeIdentifier)) {
            String msg = "Error, LCID: " + localeIdentifier + " already exists.";
            log.error(msg);
            throw new BadRequestException(msg);
        }

        languageRepository.save(new Language(localeIdentifier));
    }
}

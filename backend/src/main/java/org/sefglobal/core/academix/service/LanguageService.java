package org.sefglobal.core.academix.service;

import org.sefglobal.core.exception.BadRequestException;
import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.academix.repository.LanguageRepository;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * Retrieves all the {@link Language} objects
     *
     * @return {@link List} of {@link Language} objects
     */
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    /**
     * Add a new {@link Language} support
     *
     * @param localeIdentifier which is the LCID
     * @return the created {@link Language}
     *
     * @throws BadRequestException if validating the {@code localeIdentifier} fails
     */
    public Language addLanguage(String localeIdentifier) throws BadRequestException {
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
        Language language = new Language(localeIdentifier);
        Language savedLanguage = languageRepository.save(language);
        language.setId(savedLanguage.getId());
        return language;
    }

    /**
     * Delete a existing {@link Language}
     *
     * @param id which is the identifier of the {@link Language}
     * @return {@code true} if language gets deleted
     *
     * @throws ResourceNotFoundException if {@link Language} for {@code id} doesn't exist
     */
    public boolean deleteLanguage(long id) throws ResourceNotFoundException {
        if (!languageRepository.existsById(id)) {
            String msg = "Error, Language with id: " + id + " cannot be deleted. " +
                         "Language doesn't exist.";
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        languageRepository.deleteById(id);
        return true;
    }
}

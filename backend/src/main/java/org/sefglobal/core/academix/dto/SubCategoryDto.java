package org.sefglobal.core.academix.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class SubCategoryDto {

    private long id;

    @NotEmpty(message = "translations are mandatory")
    private List<@Valid TranslationDto> translations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TranslationDto> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationDto> translations) {
        this.translations = translations;
    }
}

package org.sefglobal.core.academix.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ItemDto {

    private long id;

    private String link;

    @NotEmpty(message = "translations are mandatory")
    private List<@Valid ItemTranslationDto> translations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<ItemTranslationDto> getTranslations() {
        return translations;
    }

    public void setTranslations(
            List<ItemTranslationDto> translations) {
        this.translations = translations;
    }
}

package org.sefglobal.core.academix.dto;

import javax.validation.constraints.NotBlank;

public class TranslationDto {

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "language is mandatory")
    private String language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

package org.sefglobal.core.academix.dto;

import javax.validation.constraints.NotBlank;

public class ItemTranslationDto extends TranslationDto {
    @NotBlank(message = "description is mandatory")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

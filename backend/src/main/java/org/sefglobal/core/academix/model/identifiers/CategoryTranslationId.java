package org.sefglobal.core.academix.model.identifiers;

import org.sefglobal.core.academix.model.Category;
import org.sefglobal.core.academix.model.Language;

import java.io.Serializable;
import java.util.Objects;

public class CategoryTranslationId implements Serializable {
    private Category category;
    private Language language;

    public CategoryTranslationId() {
    }

    public CategoryTranslationId(Category category, Language language) {
        this.category = category;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryTranslationId that = (CategoryTranslationId) o;
        return Objects.equals(category, that.category) &&
               Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, language);
    }
}

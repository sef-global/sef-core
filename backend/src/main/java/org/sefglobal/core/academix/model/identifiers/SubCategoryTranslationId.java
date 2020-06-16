package org.sefglobal.core.academix.model.identifiers;

import org.sefglobal.core.academix.model.Language;
import org.sefglobal.core.academix.model.SubCategory;

import java.io.Serializable;
import java.util.Objects;

public class SubCategoryTranslationId implements Serializable {
    private SubCategory subCategory;
    private Language language;

    public SubCategoryTranslationId() {
    }

    public SubCategoryTranslationId(SubCategory subCategory, Language language) {
        this.subCategory = subCategory;
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
        SubCategoryTranslationId that = (SubCategoryTranslationId) o;
        return Objects.equals(subCategory, that.subCategory) &&
               Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subCategory, language);
    }
}

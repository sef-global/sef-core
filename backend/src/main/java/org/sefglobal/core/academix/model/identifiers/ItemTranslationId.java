package org.sefglobal.core.academix.model.identifiers;

import org.sefglobal.core.academix.model.Item;
import org.sefglobal.core.academix.model.Language;

import java.io.Serializable;
import java.util.Objects;

public class ItemTranslationId implements Serializable {
    private Item item;
    private Language language;

    public ItemTranslationId() {
    }

    public ItemTranslationId(Item item, Language language) {
        this.item = item;
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
        ItemTranslationId that = (ItemTranslationId) o;
        return Objects.equals(item, that.item) &&
               Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, language);
    }
}

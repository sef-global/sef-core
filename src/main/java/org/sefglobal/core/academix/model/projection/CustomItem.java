package org.sefglobal.core.academix.model.projection;

import org.sefglobal.core.academix.model.ItemTranslation;

import java.util.List;

public interface CustomItem {
    Long getId();
    String getName();
    String getDescription();
    String getLink();
    List<ItemTranslation> getTranslations();
}

package org.sefglobal.core.academix.projections;

import java.util.Set;

public interface CustomItem {
    Long getId();
    String getLink();
    Set<CustomItemTranslation> getTranslations();
}

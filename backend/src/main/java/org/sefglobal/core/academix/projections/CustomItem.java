package org.sefglobal.core.academix.projections;

import java.util.List;

public interface CustomItem {
    Long getId();
    String getLink();
    List<CustomItemTranslation> getTranslations();
}

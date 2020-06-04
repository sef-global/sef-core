package org.sefglobal.core.academix.projections;

import java.util.List;

public interface CustomCategory {
    Long getId();
    List<CustomTranslation> getTranslations();
}

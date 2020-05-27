package org.sefglobal.core.academix.projections;

import java.util.Set;

public interface CustomCategory {
    Long getId();
    Set<CustomTranslation> getTranslations();
}

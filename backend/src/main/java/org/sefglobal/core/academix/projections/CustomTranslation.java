package org.sefglobal.core.academix.projections;

import org.springframework.beans.factory.annotation.Value;

public interface CustomTranslation {
    String getName();

    @Value("#{target.language.locale}")
    String getLanguage();
}

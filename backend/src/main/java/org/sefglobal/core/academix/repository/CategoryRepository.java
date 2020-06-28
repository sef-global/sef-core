package org.sefglobal.core.academix.repository;

import org.sefglobal.core.academix.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

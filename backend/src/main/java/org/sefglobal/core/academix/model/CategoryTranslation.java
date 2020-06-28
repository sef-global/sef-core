package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sefglobal.core.academix.model.identifiers.CategoryTranslationId;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "category_translation")
@IdClass(CategoryTranslationId.class)
@JsonIgnoreProperties({"createdAt", "updatedAt", "category"})
public class CategoryTranslation extends AuditModel {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
                referencedColumnName = "id",
                nullable = false)
    private Category category;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id",
                referencedColumnName = "id",
                nullable = false)
    private Language language;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

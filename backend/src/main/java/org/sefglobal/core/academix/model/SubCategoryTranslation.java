package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sefglobal.core.academix.model.identifiers.SubCategoryTranslationId;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;

@Entity
@Table(name = "sub_category_translation")
@IdClass(SubCategoryTranslationId.class)
public class SubCategoryTranslation extends AuditModel {

    @JsonIgnore
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id",
                referencedColumnName = "id",
                nullable = false)
    private SubCategory subCategory;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id",
                referencedColumnName = "id",
                nullable = false)
    private Language language;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
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

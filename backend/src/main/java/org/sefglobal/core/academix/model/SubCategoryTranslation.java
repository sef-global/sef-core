package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sefglobal.core.academix.model.identifiers.SubCategoryTranslationId;
import org.sefglobal.core.model.AuditModel;
import org.sefglobal.core.academix.util.Language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
@Table(name = "sub_category_translation")
@IdClass(SubCategoryTranslationId.class)
@JsonIgnoreProperties({"createdAt", "updatedAt", "subCategory"})
public class SubCategoryTranslation extends AuditModel {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id",
                referencedColumnName = "id",
                nullable = false)
    private SubCategory subCategory;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 5,
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

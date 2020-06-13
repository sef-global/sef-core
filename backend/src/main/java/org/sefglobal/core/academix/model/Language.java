package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "language")
public class Language extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String locale;

    @JsonIgnore
    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<CategoryTranslation> categoryTranslations;

    @JsonIgnore
    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<SubCategoryTranslation> subCategoryTranslations;

    @JsonIgnore
    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<ItemTranslation> itemTranslations;

    public Language() {
    }

    public Language(String locale) {
        this.locale = locale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<CategoryTranslation> getCategoryTranslations() {
        return categoryTranslations;
    }

    public void setCategoryTranslations(
            List<CategoryTranslation> categoryTranslations) {
        this.categoryTranslations = categoryTranslations;
    }

    public List<SubCategoryTranslation> getSubCategoryTranslations() {
        return subCategoryTranslations;
    }

    public void setSubCategoryTranslations(
            List<SubCategoryTranslation> subCategoryTranslations) {
        this.subCategoryTranslations = subCategoryTranslations;
    }

    public List<ItemTranslation> getItemTranslations() {
        return itemTranslations;
    }

    public void setItemTranslations(
            List<ItemTranslation> itemTranslations) {
        this.itemTranslations = itemTranslations;
    }
}

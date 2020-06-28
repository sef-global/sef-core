package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@JsonIgnoreProperties({"createdAt", "updatedAt", "subCategories"})
public class Category extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "category",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<CategoryTranslation> translations = new ArrayList<>();

    @OneToMany(mappedBy = "category",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<SubCategory> subCategories = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryTranslation> getTranslations() {
        return translations;
    }

    public void addTranslation(CategoryTranslation translation) {
        translation.setCategory(this);
        translations.add(translation);
    }

    public void setTranslations(List<CategoryTranslation> translations) {
        this.translations = translations;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}

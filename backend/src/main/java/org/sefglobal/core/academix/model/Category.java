package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<CategoryTranslation> translations = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "category",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<SubCategory> subCategories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CategoryTranslation> getTranslations() {
        return translations;
    }

    public void addTranslation(CategoryTranslation translation) {
        translation.setCategory(this);
        translations.add(translation);
    }

    public void setTranslations(
            List<CategoryTranslation> translations) {
        this.translations = translations;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}

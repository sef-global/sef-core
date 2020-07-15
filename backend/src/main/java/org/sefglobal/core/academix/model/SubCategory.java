package org.sefglobal.core.academix.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sefglobal.core.model.AuditModel;

@Entity
@Table(name = "sub_category")
@JsonIgnoreProperties({"createdAt", "updatedAt", "items"})
public class SubCategory extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "subCategory",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<SubCategoryTranslation> translations = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "category_id",
                referencedColumnName = "id",
                nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "subCategories")
    private List<Item> items = new ArrayList<>();

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

    public List<SubCategoryTranslation> getTranslations() {
        return translations;
    }

    public void addTranslation(SubCategoryTranslation translation) {
        translation.setSubCategory(this);
        translations.add(translation);
    }

    public void setTranslations(
            List<SubCategoryTranslation> translations) {
        this.translations = translations;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

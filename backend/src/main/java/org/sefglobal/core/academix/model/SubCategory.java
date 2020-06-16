package org.sefglobal.core.academix.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sefglobal.core.model.AuditModel;

@Entity
@Table(name = "sub_category")
public class SubCategory extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "subCategory",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<SubCategoryTranslation> translations = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
                referencedColumnName = "id",
                nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "subCategories")
    private List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Item")
@JsonIgnoreProperties({"createdAt", "updatedAt"})
public class Item extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
            nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
            nullable = false)
    private String description;

    @Column(length = 10000)
    private String link;

    @OneToMany(mappedBy = "item",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<ItemTranslation> translations = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "item_sub_category_map",
               joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sub_category_id",
                                                referencedColumnName = "id"))
    private List<SubCategory> subCategories = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<ItemTranslation> getTranslations() {
        return translations;
    }

    public void addTranslation(ItemTranslation translation) {
        translation.setItem(this);
        translations.add(translation);
    }

    public void setTranslations(
            List<ItemTranslation> translations) {
        this.translations = translations;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(SubCategory subCategory){
        subCategory.getItems().add(this);
        subCategories.add(subCategory);
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}

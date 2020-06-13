package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Item")
public class Item extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10000)
    private String link;

    @OneToMany(mappedBy = "item",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<ItemTranslation> translations = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST,
                           CascadeType.MERGE})
    @JoinTable(name = "item_sub_category_map",
               joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sub_category_id",
                                                referencedColumnName = "id"))
    private List<SubCategory> subCategories = new ArrayList<>();

    public Long getId() {
        return id;
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

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
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

@Entity
@Table(name = "sub_category")
@Getter @Setter
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
    private List<Item> items;

    public void addTranslation(SubCategoryTranslation translation) {
        translation.setSubCategory(this);
        translations.add(translation);
    }

    public void removeTranslation(SubCategoryTranslation translation) {
        translation.setSubCategory(null);
        translations.remove(translation);
    }
}

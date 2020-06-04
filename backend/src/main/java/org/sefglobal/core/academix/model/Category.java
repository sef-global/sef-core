package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter @Setter
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

    public void addTranslation(CategoryTranslation translation) {
        translation.setCategory(this);
        translations.add(translation);
    }

    // TODO check if breaks with category(nullable false) in CategoryTranslation
    public void removeTranslation(CategoryTranslation translation) {
        translation.setCategory(null);
        translations.remove(translation);
    }
}

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
@Getter @Setter
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
    private List<SubCategory> subCategories;

    public void addTranslation(ItemTranslation translation) {
        translation.setItem(this);
        translations.add(translation);
    }

    public void removeTranslation(ItemTranslation translation) {
        translation.setItem(null);
        translations.remove(translation);
    }
}

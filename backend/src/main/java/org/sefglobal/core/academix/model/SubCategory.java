package org.sefglobal.core.academix.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

    @OneToMany(mappedBy = "subCategory")
    private Set<SubCategoryTranslation> translations;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            nullable = false
    ) private Category category;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "sub_category_item_map",
            joinColumns = @JoinColumn(name = "sub_category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id")
    ) private Set<Item> items;

}

package org.sefglobal.core.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Item")
@Getter @Setter
public class Item extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10000)
    private String link;

    @OneToMany(mappedBy = "item")
    private Set<ItemTranslation> itemTranslations;

    @ManyToMany(mappedBy = "items")
    private Set<SubCategory> subCategories;

}

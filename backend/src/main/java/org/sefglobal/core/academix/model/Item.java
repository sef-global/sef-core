package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;
import java.util.Set;

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
    private Set<ItemTranslation> translations;

    @JsonIgnore
    @ManyToMany(mappedBy = "items")
    private Set<SubCategory> subCategories;

}

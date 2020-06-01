package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter @Setter
public class Category extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category")
    private Set<CategoryTranslation> translations;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<SubCategory> subCategories;

}

package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;

@Entity
@Table(name = "sub_category_translation")
@Getter @Setter
public class SubCategoryTranslation extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "sub_category_id",
            referencedColumnName = "id",
            nullable = false
    ) private SubCategory subCategory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "language_id",
            referencedColumnName = "id",
            nullable = false
    ) private Language language;

    private String name;

}

package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;
import org.sefglobal.core.model.Language;

import javax.persistence.*;

@Entity
@Table(name = "category_translation")
@Getter @Setter
public class CategoryTranslation extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            nullable = false
    ) private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "language_id",
            referencedColumnName = "id",
            nullable = false
    ) private Language language;

    private String name;

}

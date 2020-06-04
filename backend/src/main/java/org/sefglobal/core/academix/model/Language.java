package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;

@Entity
@Table(name = "language")
@Getter
@Setter
public class Language extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String locale;

    @JsonIgnore
    @OneToOne(mappedBy = "language")
    private CategoryTranslation categoryTranslation;

    @JsonIgnore
    @OneToOne(mappedBy = "language")
    private SubCategoryTranslation subCategoryTranslation;

    @JsonIgnore
    @OneToOne(mappedBy = "language")
    private ItemTranslation itemTranslation;

    public Language() {
    }

    public Language(String locale) {
        this.locale = locale;
    }
}

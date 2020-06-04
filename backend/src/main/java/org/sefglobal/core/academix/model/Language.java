package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "language")
@Getter @Setter
public class Language extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String locale;

    @JsonIgnore
    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<CategoryTranslation> categoryTranslations;

    @JsonIgnore
    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<SubCategoryTranslation> subCategoryTranslations;

    @JsonIgnore
    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<ItemTranslation> itemTranslations;

    public Language() {
    }

    public Language(String locale) {
        this.locale = locale;
    }
}

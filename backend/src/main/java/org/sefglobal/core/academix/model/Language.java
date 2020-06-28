package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "language")
@Getter @Setter
@JsonIgnoreProperties({"createdAt", "updatedAt",
                       "categoryTranslations", "subCategoryTranslations", "itemTranslations",
                       "hibernateLazyInitializer", "handler"})
public class Language extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String locale;

    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<CategoryTranslation> categoryTranslations;

    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<SubCategoryTranslation> subCategoryTranslations;

    @OneToMany(mappedBy = "language", orphanRemoval = true)
    private List<ItemTranslation> itemTranslations;

    public Language() {
    }

    public Language(String locale) {
        this.locale = locale;
    }

    public Language(Long id, String locale) {
        this.id = id;
        this.locale = locale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

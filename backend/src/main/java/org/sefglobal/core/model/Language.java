package org.sefglobal.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "language")
@Getter @Setter
public class Language extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String locale;

    @OneToOne(mappedBy = "language")
    private CategoryTranslation categoryTranslation;

    @OneToOne(mappedBy = "language")
    private SubCategoryTranslation subCategoryTranslation;

    @OneToOne(mappedBy = "language")
    private ItemTranslation itemTranslation;

    public Language() {
    }

    public Language(String locale) {
        this.locale = locale;
    }
}

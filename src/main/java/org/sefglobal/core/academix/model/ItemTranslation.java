package org.sefglobal.core.academix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sefglobal.core.academix.model.identifiers.ItemTranslationId;
import org.sefglobal.core.model.AuditModel;
import org.sefglobal.core.academix.util.Language;

import javax.persistence.*;

@Entity
@Table(name = "item_translation")
@IdClass(ItemTranslationId.class)
@JsonIgnoreProperties({"createdAt", "updatedAt", "item"})
public class ItemTranslation extends AuditModel {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id",
                referencedColumnName = "id",
                nullable = false)
    private Item item;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 5,
            nullable = false)
    private Language language;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String description;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

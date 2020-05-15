package org.sefglobal.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class University extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @NotNull
    @Column(name = "status", length = 10)
    private String status = "ACTIVE";

    public void setStatus(String status) {
        this.status = status;
    }
}

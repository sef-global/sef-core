package org.sefglobal.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ambassador extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private University university;

    @NotNull
    @Column(name = "status", length = 10)
    private String status = "ACTIVE";

    public long getId() {
        return id;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

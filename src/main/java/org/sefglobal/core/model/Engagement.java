package org.sefglobal.core.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.sefglobal.core.model.identity.EngagementIdentity;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class Engagement extends AuditModel {

    @EmbeddedId
    EngagementIdentity engagementIdentity;

    public void setEngagementIdentity(EngagementIdentity engagementIdentity) {
        this.engagementIdentity = engagementIdentity;
    }
}

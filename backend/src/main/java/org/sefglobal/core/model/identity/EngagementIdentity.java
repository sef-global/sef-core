package org.sefglobal.core.model.identity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.sefglobal.core.model.Ambassador;
import org.sefglobal.core.model.Event;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class EngagementIdentity implements Serializable {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ambassador ambassador;

    @NotNull
    private String hash;

    public EngagementIdentity() {
    }

    public EngagementIdentity(Event event, Ambassador ambassador, String hash) {
        this.event = event;
        this.ambassador = ambassador;
        this.hash = hash;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setAmbassador(Ambassador ambassador) {
        this.ambassador = ambassador;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Event getEvent() {
        return event;
    }

    public Ambassador getAmbassador() {
        return ambassador;
    }

    public String getHash() {
        return hash;
    }
}

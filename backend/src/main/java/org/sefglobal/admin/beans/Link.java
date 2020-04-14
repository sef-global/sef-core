package org.sefglobal.admin.beans;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Link {

    private Society society;
    private String key;

    public Link(Society society, int eventId) {
        this.society = society;
        this.key = createKey(eventId, society.getId());
    }

    public Society getSociety() {
        return society;
    }

    public void setSociety(Society society) {
        this.society = society;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String createKey(int eventId, int societyId) {
        return Base64.getEncoder().encodeToString((eventId + ":" + societyId).getBytes(
                StandardCharsets.UTF_8));
    }
}

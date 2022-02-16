package org.sefglobal.core.model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Link {
    private String token;
    private Ambassador ambassador;

    /**
     * Generate a token for the url. This is a Base64 encoded token in the following format
     * eventId:ambassadorId
     *
     * @param eventId ID of the event
     */
    public void generateToken(long eventId) {
        this.token = Base64.getEncoder()
                .encodeToString((eventId + ":" + this.ambassador.getId()).getBytes(StandardCharsets.UTF_8));
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Ambassador getAmbassador() {
        return ambassador;
    }

    public void setAmbassador(Ambassador ambassador) {
        this.ambassador = ambassador;
    }
}

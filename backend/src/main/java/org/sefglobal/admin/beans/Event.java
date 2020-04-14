package org.sefglobal.admin.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represent an event in SEF. This can be a event or a campaign
 */
public class Event {

    private int id;
    private String name;
    private String url;
    private long eventTime;
    private long createdAt;
    private String status;

    public Event() {
    }

    /**
     * Returns a new Event object using SQL result set
     *
     * @param resultSet a SQL result set object containing an event
     */
    public Event(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.url = resultSet.getString("url");
        this.eventTime = resultSet.getLong("event_time");
        this.createdAt = resultSet.getLong("created_at");
        this.status = resultSet.getString("status");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}

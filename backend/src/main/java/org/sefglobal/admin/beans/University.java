package org.sefglobal.admin.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represent a University registered with SEF
 */
public class University {

    private int id;
    private String name;
    private String ambassadorName;
    private String imageUrl;
    private String status;

    public University() {
    }

    public University(int id, String name, String ambassadorName, String imageUrl, String status) {
        this.id = id;
        this.name = name;
        this.ambassadorName = ambassadorName;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    /**
     * Returns a new University object using SQL result set
     *
     * @param resultSet a SQL result set object containing an event
     */
    public University(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.ambassadorName = resultSet.getString("ambassador_name");
        this.imageUrl = resultSet.getString("image_url");
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

    public String getAmbassadorName() {
        return ambassadorName;
    }

    public void setAmbassadorName(String ambassadorName) {
        this.ambassadorName = ambassadorName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

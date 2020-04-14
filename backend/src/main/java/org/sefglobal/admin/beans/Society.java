package org.sefglobal.admin.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represent a society within a university
 */
public class Society {

    private int id;
    private String name;
    private String imageUrl;
    private int universityId;
    private String status;

    public Society() {
    }

    public Society(int id, String name, String imageUrl, int universityId, String status) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.universityId = universityId;
        this.status = status;
    }

    public Society(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.imageUrl = resultSet.getString("image_url");
        this.universityId = resultSet.getInt("university_id");
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package org.sefglobal.admin.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent a university with engagement count
 */
public class RankedUniversity extends University {

    private int engagement;
    private List<RankedSociety> societies = new ArrayList<>();

    public RankedUniversity() {
    }

    public RankedUniversity(University university) {
        super(
                university.getId(),
                university.getName(),
                university.getAmbassadorName(),
                university.getImageUrl(),
                university.getStatus()
        );
    }

    public RankedUniversity(ResultSet resultSet) throws SQLException {
        super(resultSet);
        this.engagement = resultSet.getInt("engagement");
    }

    public int getEngagement() {
        return engagement;
    }

    public void setEngagement(int engagement) {
        this.engagement = engagement;
    }

    public List<RankedSociety> getSocieties() {
        return societies;
    }

    public void setSocieties(List<RankedSociety> societies) {
        this.societies = societies;
    }
}

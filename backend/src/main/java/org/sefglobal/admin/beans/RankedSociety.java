package org.sefglobal.admin.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankedSociety extends Society {
    private int engagement;

    public RankedSociety() {
    }

    public RankedSociety(ResultSet resultSet) throws SQLException {
        super(resultSet);
        this.engagement = resultSet.getInt("engagement");
    }

    public int getEngagement() {
        return engagement;
    }

    public void setEngagement(int engagement) {
        this.engagement = engagement;
    }
}

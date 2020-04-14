package org.sefglobal.admin.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represent a University registered with SEF
 */
public class CensoredUniversity extends University {

    private String ambassadorEmail;

    public CensoredUniversity() {

    }

    public CensoredUniversity(ResultSet resultSet) throws SQLException {
        super(resultSet);
        this.ambassadorEmail = resultSet.getString("ambassador_email");
    }

    public String getAmbassadorEmail() {
        return ambassadorEmail;
    }

    public void setAmbassadorEmail(String ambassadorEmail) {
        this.ambassadorEmail = ambassadorEmail;
    }
}

package org.sefglobal.admin.dao;

import org.sefglobal.admin.beans.Event;
import org.sefglobal.admin.beans.RankedSociety;
import org.sefglobal.admin.beans.RankedUniversity;
import org.sefglobal.admin.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EngagementDAO {

    private Logger logger = LoggerFactory.getLogger(EngagementDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UniversityDAO universityDAO;

    /**
     * Returns all Ranked Universities by engagement count
     *
     * @return List of RankedUniversity
     */
    public List<RankedUniversity> getUniversityRanking() {
        String sqlQuery = "" +
                "SELECT " +
                "   u.id," +
                "   u.name," +
                "   u.ambassador_name," +
                "   u.image_url, " +
                "   u.status, " +
                "   COUNT(v.event_id) as engagement " +
                "FROM " +
                "   university u " +
                "INNER JOIN " +
                "   society s " +
                "ON " +
                "   u.id = s.university_id " +
                "INNER JOIN " +
                "   engagement v " +
                "ON " +
                "   s.id = v.society_id " +
                "WHERE " +
                "   u.status='ACTIVE' " +
                "GROUP BY " +
                "   u.id " +
                "ORDER BY " +
                "   engagement DESC";

        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    (rs, rowNum) -> new RankedUniversity(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get all university info", e);
        }
        return null;
    }


    public RankedUniversity  getEngagementByUniversity(int id) throws ResourceNotFoundException {
        String sqlQuery = "" +
                "SELECT " +
                "   s.*, " +
                "   COUNT(e.event_id) as engagement " +
                "FROM " +
                "   university u " +
                "INNER JOIN " +
                "   society s " +
                "ON " +
                "   u.id = s.university_id " +
                "INNER JOIN " +
                "   engagement e " +
                "ON " +
                "   s.id = e.society_id " +
                "WHERE " +
                "   u.id = ? " +
                "GROUP BY " +
                "   s.id;";
        RankedUniversity rankedUniversity =  new RankedUniversity(universityDAO.getUniversity(id));
        // use atomic integer to count total engagement -- local variables are not allowed inside lambda functions
        AtomicInteger totalEngagement = new AtomicInteger(0);
        try {
            List<RankedSociety> rankedSocieties = jdbcTemplate.query(
                    sqlQuery,
                    new Object[]{id},
                    (rs, rowNum) -> {
                        totalEngagement.set(totalEngagement.addAndGet(rs.getInt("engagement")));
                        return new RankedSociety(rs);
                    }
            );
            rankedUniversity.setSocieties(rankedSocieties);
            rankedUniversity.setEngagement(totalEngagement.get());

            return rankedUniversity;
        } catch (DataAccessException e) {
            logger.error("Unable to get all university info", e);
        }
        return null;
    }

    /**
     * Adds an engagement to the DB
     *
     * @param eventId   eventId
     * @param societyId societyId
     * @param ip        ip address of the visitor
     */
    private void saveEngagement(int eventId, int societyId, String ip) {

        // The interval between two engagements
        final int SECONDS_TO_SKIP = 100;

        long timestamp = new Date().getTime() / 1000;

        // Generate the interval value
        int intervalValue = (int) (timestamp / SECONDS_TO_SKIP);

        String sqlQuery = "" +
                "INSERT INTO " +
                "   engagement(" +
                "       event_id, " +
                "       society_id, " +
                "       ip," +
                "       created_at, " +
                "       interval_value " +
                "   ) " +
                "VALUES " +
                "   (?,?,?,?,?)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
                ps.setInt(1, eventId);
                ps.setInt(2,societyId);
                ps.setString(3,ip);
                ps.setLong(4,timestamp);
                ps.setInt(5, intervalValue);
                return ps;
            });
        } catch (DuplicateKeyException ex) {
            // Do nothing if engagement duplicates within the interval
        } catch (DataAccessException e) {
            logger.error("Unable to add engagement", e);
        }
    }

    /**
     * Creates an engagement and adds to the DB
     *
     * @param eventId   eventId
     * @param societyId societyId
     * @param ip        ip address of the visitor
     */
    public Event createEngagement(int eventId, int societyId, String ip) throws ResourceNotFoundException {
        Event event = eventDAO.getEvent(eventId);
        // Check if the event exists
        if (event != null) {
            this.saveEngagement(eventId, societyId, ip);
            return event;
        }
        throw new ResourceNotFoundException("Event not found");
    }

}

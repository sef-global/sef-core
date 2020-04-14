package org.sefglobal.admin.dao;

import org.sefglobal.admin.beans.Event;
import org.sefglobal.admin.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

@Repository
public class EventDAO {

    private Logger logger = LoggerFactory.getLogger(EventDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Gets an event by eventId
     *
     * @param id eventId
     * @return Event
     */
    public Event getEvent(int id) {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   event " +
                "WHERE " +
                "   id=? " +
                "   AND " +
                "   status='ACTIVE'";
        try {
            return (Event) jdbcTemplate.queryForObject(
                    sqlQuery,
                    new Object[]{id},
                    (rs, rowNum) -> new Event(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get info of '" + id + "'", e);
        }
        return null;
    }

    public List<Event> getAllEvents() {
        String sqlQuery = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   event " +
                "WHERE " +
                "   status = 'ACTIVE'";
        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    (rs, rowNum) -> new Event(rs)
            );
        } catch (DataAccessException e) {
            logger.error("Unable to get event info", e);
        }
        return null;
    }

    public Event addEvent(Event event) throws BadRequestException {
        long currentTimestamp = new Date().getTime() / 1000;
        String sqlQuery = "" +
                "INSERT INTO" +
                "   event (" +
                "       name, " +
                "       url," +
                "       event_time," +
                "       created_at," +
                "       status" +
                "   ) " +
                "VALUES " +
                "   (?,?,?,?,'ACTIVE')";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"id"});
                ps.setString(1, event.getName());
                ps.setString(2, event.getUrl());
                ps.setLong(3, event.getEventTime());
                ps.setLong(4, currentTimestamp);
                return ps;
            }, keyHolder);
            int key = keyHolder.getKey().intValue();
            return getEvent(key);
        } catch (DataAccessException e) {
            logger.error("Unable to get event info", e);
        }
        return null;
    }
}

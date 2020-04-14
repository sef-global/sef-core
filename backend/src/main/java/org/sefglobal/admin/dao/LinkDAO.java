package org.sefglobal.admin.dao;

import org.sefglobal.admin.beans.Link;
import org.sefglobal.admin.beans.Society;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LinkDAO {

    private Logger logger = LoggerFactory.getLogger(LinkDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment environment;

    public List<Link> getAllLinksByEvent(int eventId){
        String sqlQuery = "SELECT * FROM society WHERE status='ACTIVE'";
        try {
            return jdbcTemplate.query(
                    sqlQuery,
                    (rs, rowNum) ->  new Link(new Society(rs), eventId)
            );
        }catch (DataAccessException e){
            logger.error("Unable to get link info", e);
        }
        return null;
    }
}

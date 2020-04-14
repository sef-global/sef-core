package org.sefglobal.admin.api;

import org.sefglobal.admin.beans.Event;
import org.sefglobal.admin.beans.RankedUniversity;
import org.sefglobal.admin.dao.EngagementDAO;
import org.sefglobal.admin.dao.EventDAO;
import org.sefglobal.admin.exception.AdminAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EngagementManagementAPI {

    @Autowired
    private EngagementDAO engagementDAO;

    @GetMapping("/engagements")
    public List<RankedUniversity> getUniversityRanking(){
        return engagementDAO.getUniversityRanking();
    }

    @PostMapping("/engagements")
    public Event addEngagement(@RequestBody Map<String,String> body) throws AdminAPIException {
        int eventId = Integer.parseInt(body.get("eventId"));
        int societyId = Integer.parseInt(body.get("societyId"));
        String ip = body.get("ip");
        return engagementDAO.createEngagement(eventId, societyId, ip);
    }

    @GetMapping("/engagements/university/{id}")
    public RankedUniversity getEngagementByUniversity(@PathVariable int id) throws
                                                                            AdminAPIException {
        return engagementDAO.getEngagementByUniversity(id);
    }
}

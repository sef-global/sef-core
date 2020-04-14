package org.sefglobal.admin.api;

import org.sefglobal.admin.beans.Event;
import org.sefglobal.admin.dao.EventDAO;
import org.sefglobal.admin.exception.AdminAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventManagementAPI {
    @Autowired
    private EventDAO eventDAO;

    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return eventDAO.getAllEvents();
    }

    @PostMapping("/events")
    public Event addEvent(@RequestBody Event event) throws AdminAPIException {
        return eventDAO.addEvent(event);
    }
}

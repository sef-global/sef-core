package org.sefglobal.admin.api;

import org.sefglobal.admin.beans.Link;
import org.sefglobal.admin.dao.LinkDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LinkManagementAPI {
    @Autowired
    private LinkDAO linkDAO;

    @GetMapping("/events/{eventId}/links")
    public List<Link> getAllLinksByEvent(@PathVariable int eventId){
        return linkDAO.getAllLinksByEvent(eventId);
    }
}

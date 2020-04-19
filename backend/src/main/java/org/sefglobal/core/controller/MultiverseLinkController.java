package org.sefglobal.core.controller;

import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.model.Ambassador;
import org.sefglobal.core.model.Engagement;
import org.sefglobal.core.model.Event;
import org.sefglobal.core.model.identity.EngagementIdentity;
import org.sefglobal.core.repository.AmbassadorRepository;
import org.sefglobal.core.repository.EngagementRepository;
import org.sefglobal.core.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;

@RestController
public class MultiverseLinkController {

    final Logger logger = LoggerFactory.getLogger(MultiverseLinkController.class);
    // The interval between two engagements (ms)
    final int ENGAGEMENT_INTERVAL_PERIOD = 3600 * 1000;
    @Autowired
    AmbassadorRepository ambassadorRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EngagementRepository engagementRepository;

    @GetMapping("/link/{token}")
    public void redirectToEventUrl(@PathVariable String token,
            HttpServletRequest request,
            HttpServletResponse response) throws ResourceNotFoundException {
        // Extract Event and Society ids from the token
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);
        String[] linkDetails = decodedString.split(":");
        if (linkDetails.length != 2) {
            String message = "Incorrect Url";
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
        int eventId = Integer.parseInt(linkDetails[0]);
        int ambassadorId = Integer.parseInt(linkDetails[1]);
        Event event = eventRepository.findByIdAndStatus(eventId, "ACTIVE");
        if (event != null) {
            Ambassador ambassador = ambassadorRepository.findByIdAndStatus(ambassadorId, "ACTIVE");
            if (ambassador != null) {
                // Generate a basic hash which is unique for the given interval period
                String hash =
                        getClientIp(request) + (new Date().getTime() / ENGAGEMENT_INTERVAL_PERIOD);
                Engagement engagement = new Engagement();
                engagement.setEngagementIdentity(new EngagementIdentity(event, ambassador, hash));
                engagementRepository.save(engagement);
            }
            // Redirect to the given link for the event
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", event.getLink());
            response.setHeader("Connection", "close");
        } else {
            String message = "No active events. Incorrect Url.";
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    /**
     * Extract and returns the client's ip address from a given request
     *
     * @param request User's request
     * @return User's ip address
     */
    private static String getClientIp(HttpServletRequest request) {
        String ipAddress = null;
        if (request != null) {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || ipAddress.equals("")) {
                ipAddress = request.getRemoteAddr();
            }
        }
        return ipAddress;
    }
}

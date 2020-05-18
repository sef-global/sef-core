package org.sefglobal.core.controller;

import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.model.Ambassador;
import org.sefglobal.core.model.Event;
import org.sefglobal.core.model.Link;
import org.sefglobal.core.model.University;
import org.sefglobal.core.repository.AmbassadorRepository;
import org.sefglobal.core.repository.EventRepository;
import org.sefglobal.core.repository.UniversityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/multiverse")
public class MultiverseController {

    final Logger logger = LoggerFactory.getLogger(MultiverseController.class);
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AmbassadorRepository ambassadorRepository;
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/universities")
    public Iterable<University> listUniversities() {
        return universityRepository.findAll();
    }

    @GetMapping("/universities/{id}")
    public Optional<University> getUniversityById(@PathVariable long id) {
        return universityRepository.findById(id);
    }

    @PostMapping("/universities")
    public University createUniversity(@Valid @RequestBody University university) {
        return universityRepository.save(university);
    }

    @PutMapping("/universities/{id}")
    public University updateUniversity(@PathVariable long id,
            @Valid @RequestBody University university) throws ResourceNotFoundException {
        if (universityRepository.existsById(id)) {
            return universityRepository.save(university);
        } else {
            String message = "University does not exists for university id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @DeleteMapping("/universities/{id}")
    public University deleteUniversity(@PathVariable long id) throws ResourceNotFoundException {
        if (universityRepository.existsById(id)) {
            University university = universityRepository.getOne(id);
            university.setStatus("REMOVED");
            return universityRepository.save(university);
        } else {
            String message = "University does not exists for university id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @GetMapping("/ambassadors")
    public Iterable<Ambassador> listAmbassador() {
        return ambassadorRepository.findAll();
    }

    @GetMapping("/ambassadors/{id}")
    public Optional<Ambassador> getAmbassadorById(@PathVariable long id) {
        return ambassadorRepository.findById(id);
    }

    @PostMapping("/universities/{id}/ambassadors")
    public Ambassador createAmbassador(@PathVariable long id,
            @Valid @RequestBody Ambassador ambassador) throws ResourceNotFoundException {
        return universityRepository.findById(id).map(university -> {
            ambassador.setUniversity(university);
            return ambassadorRepository.save(ambassador);
        }).orElseThrow(() -> new ResourceNotFoundException("University not found"));
    }

    @DeleteMapping("/ambassadors/{id}")
    public Ambassador deleteAmbassador(@PathVariable long id) throws ResourceNotFoundException {
        if (ambassadorRepository.existsById(id)) {
            Ambassador ambassador = ambassadorRepository.getOne(id);
            ambassador.setStatus("REMOVED");
            return ambassadorRepository.save(ambassador);
        } else {
            String message = "Ambassador does not exists for ambassador id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @PutMapping("/ambassadors/{id}")
    public Ambassador updateAmbassador(@PathVariable long id,
            @Valid @RequestBody Ambassador ambassador) throws ResourceNotFoundException {
        if (ambassadorRepository.existsById(id)) {
            return ambassadorRepository.save(ambassador);
        } else {
            String message = "Ambassador does not exists for ambassador id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @GetMapping("/events")
    public Iterable<Event> listEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/events/{id}")
    public Optional<Event> getEventById(@PathVariable long id) {
        return eventRepository.findById(id);
    }

    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    @PutMapping("/events/{id}")
    public Event updateEvent(@PathVariable long id,
            @Valid @RequestBody Event event) throws ResourceNotFoundException {
        if (eventRepository.existsById(id)) {
            return eventRepository.save(event);
        } else {
            String message = "Event does not exists for event id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @DeleteMapping("/events/{id}")
    public Event deleteEvent(@PathVariable long id) throws ResourceNotFoundException {
        if (eventRepository.existsById(id)) {
            Event event = eventRepository.getOne(id);
            event.setStatus("REMOVED");
            return eventRepository.save(event);
        } else {
            String message = "Event does not exists for event id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @GetMapping("/events/{id}/links")
    public List<Link> getEventLinks(@PathVariable long id) throws ResourceNotFoundException {
        if (eventRepository.existsByIdAndStatus(id, "ACTIVE")) {
            List<Ambassador> ambassadors = ambassadorRepository.findAllByStatus("ACTIVE");
            List<Link> links = new ArrayList<>();
            for (Ambassador ambassador: ambassadors) {
                Link link = new Link();
                link.setAmbassador(ambassador);
                link.generateToken(id);
                links.add(link);
            }
            return links;
        } else {
            String message = "Event does not exists for event id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }
}

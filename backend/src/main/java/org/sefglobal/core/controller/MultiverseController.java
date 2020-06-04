package org.sefglobal.core.controller;

import org.hibernate.validator.constraints.Range;
import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.model.*;
import org.sefglobal.core.repository.AmbassadorRepository;
import org.sefglobal.core.repository.EventRepository;
import org.sefglobal.core.repository.UniversityRepository;
import org.sefglobal.core.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
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
    @ResponseStatus(HttpStatus.OK)
    public Iterable<University> listUniversities() {
        return universityRepository.findAll();
    }

    @GetMapping("/universities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<University> getUniversityById(@PathVariable long id) {
        return universityRepository.findById(id);
    }

    @PostMapping("/universities")
    @ResponseStatus(HttpStatus.CREATED)
    public University createUniversity(@Valid @RequestBody University university) {
        return universityRepository.save(university);
    }

    @PutMapping("/universities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateUniversity(@PathVariable long id,
            @Valid @RequestBody University university) throws ResourceNotFoundException {
        if (universityRepository.existsById(id)) {
            universityRepository.save(university);
            return "Successfully updated. University id : " + id;
        } else {
            String message = "University does not exists for university id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @DeleteMapping("/universities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteUniversity(@PathVariable long id) throws ResourceNotFoundException {
        Optional<University> university = universityRepository.findById(id);
        if (university.isPresent()) {
            university.get().setStatus(Status.REMOVED.toString());
            universityRepository.save(university.get());
            return "Successfully deleted university with university id : " + id;
        } else {
            String message = "University does not exists for university id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @GetMapping("/ambassadors")
    @ResponseStatus(HttpStatus.OK)
    public Page<Ambassador> getAmbassadors(@Range(min = 1, max = 20) @RequestParam int limit,
            @RequestParam int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, limit);
        return ambassadorRepository.findAll(pageable);
    }

    @GetMapping("/ambassadors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Ambassador> getAmbassadorById(@PathVariable long id) {
        return ambassadorRepository.findById(id);
    }

    @PostMapping("/universities/{id}/ambassadors")
    @ResponseStatus(HttpStatus.CREATED)
    public Ambassador createAmbassador(@PathVariable long id,
            @Valid @RequestBody Ambassador ambassador) throws ResourceNotFoundException {
        return universityRepository.findById(id).map(university -> {
            ambassador.setUniversity(university);
            return ambassadorRepository.save(ambassador);
        }).orElseThrow(() -> new ResourceNotFoundException("University not found"));
    }

    @DeleteMapping("/ambassadors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteAmbassador(@PathVariable long id) throws ResourceNotFoundException {
        Optional<Ambassador> ambassador = ambassadorRepository.findById(id);
        if (ambassador.isPresent()) {
            ambassador.get().setStatus(Status.REMOVED.toString());
            ambassadorRepository.save(ambassador.get());
            return "Successfully deleted ambassador with ambassador id : " + id;
        } else {
            String message = "Ambassador does not exists for ambassador id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @PutMapping("/ambassadors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateAmbassador(@PathVariable long id,
            @Valid @RequestBody Ambassador ambassador) throws ResourceNotFoundException {
        if (ambassadorRepository.existsById(id)) {
            ambassadorRepository.save(ambassador);
            return "Successfully updated. Ambassador id : " + id;
        } else {
            String message = "Ambassador does not exists for ambassador id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Event> listEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Event> getEventById(@PathVariable long id) {
        return eventRepository.findById(id);
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    @PutMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateEvent(@PathVariable long id,
            @Valid @RequestBody Event event) throws ResourceNotFoundException {
        if (eventRepository.existsById(id)) {
            eventRepository.save(event);
            return "Update successful. Event id = " + id;
        } else {
            String message = "Event does not exists for event id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @DeleteMapping("/events/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteEvent(@PathVariable long id) throws ResourceNotFoundException {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            event.get().setStatus(Status.REMOVED.toString());
            eventRepository.save(event.get());
            return "Successfully deleted event with event id : " + id;
        } else {
            String message = "Event does not exists for event id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @GetMapping("/events/{id}/links")
    @ResponseStatus(HttpStatus.OK)
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

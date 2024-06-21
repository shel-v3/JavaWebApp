package com.calendar.calendar.Contoller;

import com.calendar.calendar.Models.Event;
import com.calendar.calendar.Service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/events")
public class EventController {
    private final EventService eventService;

    @GetMapping("/all-events")
    @PreAuthorize("isAuthenticated()")
    public List<Event> findAll(){
        return eventService.findAll();
    }
    @PostMapping("/insert-event")
    @PreAuthorize("isAuthenticated()")
    public void insert(@RequestBody Event event){
        eventService.insert(event);
    }
    @PutMapping("/save-event")
    @PreAuthorize("isAuthenticated()")
    public void save(@RequestBody Event event){
        eventService.save(event);
    }
    @DeleteMapping("/delete-event")
    @PreAuthorize("isAuthenticated()")
    public void delete(@RequestBody Event event){
        eventService.delete(event);
    }
    @GetMapping("/events-these-week")
    @PreAuthorize("isAuthenticated()")
    public List<Event> eventThisWeek(){
        return eventService.eventThisWeek();
    }
    @GetMapping("/future-events")
    @PreAuthorize("isAuthenticated()")
    public List<Event> futureEvent(){
        return eventService.futureEvent();
    }
    @PostMapping("/events-by-name/{name}")
    @PreAuthorize("isAuthenticated()")
    public List<Event> eventsByName(@PathVariable String name){
        return eventService.eventsByName(name);
    }
    @PostMapping("/events-by-date/{date}")
    @PreAuthorize("isAuthenticated()")
    public List<Event> eventsByDate(@PathVariable LocalDate date){
        return eventService.eventsByDate(date);
    }
}

package com.calendar.calendar.Service;

import com.calendar.calendar.Models.Event;
import com.calendar.calendar.Models.User;
import com.calendar.calendar.Repository.EventRepository;
import com.calendar.calendar.Repository.UserRepository;
import com.nimbusds.jose.crypto.impl.PRFParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<Event> findAll(){
        return eventRepository.findAll().stream().peek(
                event -> {
                    User user = userRepository.findById(event.getUserId()).orElse(null);
                    event.setUser(user);
                }
        ).toList();
    }

    public void insert(Event event){
        eventRepository.insert(event);
    }

    public void save(Event event){
        eventRepository.save(event);
    }

    public void delete(Event event){
        eventRepository.delete(event);
    }

    public List<Event> eventThisWeek() {
        LocalDateTime today = LocalDateTime.now();

        LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate();
        LocalDate weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate();

        return findAll().stream()
                .filter(event -> {
                    LocalDate eventDate = event.getStart().toLocalDate();
                    return !eventDate.isBefore(weekStart) && !eventDate.isAfter(weekEnd);
                })
                .toList();
    }

    public List<Event> futureEvent(){
        return findAll().stream()
                .filter(event -> {
                    LocalDate eventDate = event.getStart().toLocalDate();
                    return eventDate.isAfter(LocalDate.now());
                })
                .toList();
    }

    public List<Event> eventsByName(String name){
        return findAll().stream().filter(
                event -> event.getName().contains(name)
        ).toList();
    }

    public List<Event> eventsByDate(LocalDate date){
        return findAll().stream()
                .filter(event -> {
                    LocalDate eventDate = event.getStart().toLocalDate();
                    return eventDate == date;
                })
                .toList();
    }
}

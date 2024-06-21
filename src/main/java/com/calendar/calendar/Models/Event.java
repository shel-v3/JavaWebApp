package com.calendar.calendar.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "event")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    private String Name;
    private String Description;
    private LocalDateTime Start;
    private LocalDateTime End;
    private String UserId;

    @Transient
    private User User;
}

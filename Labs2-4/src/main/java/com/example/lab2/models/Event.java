package com.example.lab2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "myEvent")
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "action")
    private String action;
    @Column(name = "entity")
    private String entity;
    @Column(name = "value")
    private String value;


    public Event(String action, String entity, String value) {
        this.action = action;
        this.entity = entity;
        this.value = value;
    }
}

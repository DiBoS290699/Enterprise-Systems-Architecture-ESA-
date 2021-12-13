package com.example.lab2.jms;

import com.example.lab2.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class Sender {

    @Autowired
    private JmsTemplate jmsTemplate;

    private final String destinationName = "dataBaseWatchDog";

    public void sendUpdateEvent(String entity, Object value) {
        Event event = new Event("Update", entity, value.toString());
        jmsTemplate.convertAndSend(destinationName, event);
    }

    public void sendInsertEvent(String entity, Object value) {
        Event event = new Event("Insert", entity, value.toString());
        jmsTemplate.convertAndSend(destinationName, event);
    }

    public void sendDeleteEvent(String entity, Object value) {
        Event event = new Event("Delete", entity, value.toString());
        jmsTemplate.convertAndSend(destinationName, event);
    }
}

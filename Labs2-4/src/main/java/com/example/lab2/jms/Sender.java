package com.example.lab2.jms;

import com.example.lab2.models.Event;
//import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Topic;
import java.util.ArrayList;

@Component
//@Scope("prototype")
public class Sender {

    JmsTemplate jmsTemplate;
    Topic eventTopic;

    public Sender(JmsTemplate jmsTemplate) throws JMSException {
        this.jmsTemplate = jmsTemplate;
        this.eventTopic = jmsTemplate.getConnectionFactory().createConnection().createSession().createTopic("event");
    }

    public void updateListeners(Event event) {
        jmsTemplate.convertAndSend(eventTopic, event);
    }

    public void sendUpdateEvent(String entity, Object value) {
        Event event = new Event("Update", entity, value.toString());
        updateListeners(event);
    }

    public void sendInsertEvent(String entity, Object value) {
        Event event = new Event("Insert", entity, value.toString());
        updateListeners(event);
    }

    public void sendDeleteEvent(String entity, Object value) {
        Event event = new Event("Delete", entity, value.toString());
        updateListeners(event);
    }
}

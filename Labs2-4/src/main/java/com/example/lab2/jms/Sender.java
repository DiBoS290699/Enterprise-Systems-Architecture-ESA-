package com.example.lab2.jms;

import com.example.lab2.models.Event;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Scope("prototype")
public class Sender {

    ArrayList<EventListener> listenerList = new ArrayList<>();

    public void subscribe(EventListener listener) {
        listenerList.add(listener);
    }

    public void updateListeners(Event event) {
        for (EventListener listener: listenerList)
            listener.update(event);
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

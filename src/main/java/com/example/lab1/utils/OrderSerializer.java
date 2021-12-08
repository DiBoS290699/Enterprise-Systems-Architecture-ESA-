package com.example.lab1.utils;

import com.example.lab1.models.Order;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class OrderSerializer extends StdSerializer<Order> {

    protected OrderSerializer(Class<Order> c){
        super(c);
    }

    @Override
    public void serialize(Order order, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", order.getID());
        jsonGenerator.writeStringField("info", order.getInfo());
        jsonGenerator.writeStringField("term", order.getTerm());
        jsonGenerator.writeStringField("status", order.getStatus());
        jsonGenerator.writeEndObject();
    }
}

package com.example.lab1.utils;

import com.example.lab1.models.Client;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class ClientSerializer extends StdSerializer<Client> {

    protected ClientSerializer(Class<Client> t) { super(t); }

    @Override
    public void serialize(Client client, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", client.getID());
        jsonGenerator.writeStringField("fullName", client.getFullName());
        jsonGenerator.writeStringField("phone", client.getPhone());
        jsonGenerator.writeStringField("email", client.getEmail());
        jsonGenerator.writeEndObject();
    }
}

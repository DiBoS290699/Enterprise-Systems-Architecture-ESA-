package com.example.lab1.utils;

import com.example.lab1.models.Employee;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EmployeeSerializer extends StdSerializer<Employee> {

    protected EmployeeSerializer(Class<Employee> e){
        super(e);
    }

    @Override
    public void serialize(Employee employee, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", employee.getID());
        jsonGenerator.writeStringField("fullName", employee.getFullName());
        jsonGenerator.writeStringField("post", employee.getPost());
        jsonGenerator.writeNumberField("experience", employee.getExperience());
        jsonGenerator.writeStringField("phone", employee.getPhone());
        jsonGenerator.writeStringField("email", employee.getEmail());
        jsonGenerator.writeEndObject();
    }
}

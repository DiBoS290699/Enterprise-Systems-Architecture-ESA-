package com.example.lab1.controller;

import com.example.lab1.DAO.EmployeeDao;
import com.example.lab1.models.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/employee")
public class EmployeeController {
    @EJB
    private EmployeeDao employeeDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // GET localhost:8080/employee/
    public Response getEmployees() throws JsonProcessingException {
        List<Employee> employees = employeeDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(employees))
                .build();
    }

    @GET
    @Path("/{employeeId}")
    public Response getEmployeeById(@PathParam("employeeId") String employeeId) throws JsonProcessingException {
        Employee employee = employeeDao.get(Integer.valueOf(employeeId));
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(employee))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewEmployee(
            @FormParam("fullName") String fullName,
            @FormParam("post") String post,
            @FormParam("experience") String experience,
            @FormParam("phone") String phone,
            @FormParam("email") String email) {
        Employee employee = new Employee();
        employee.setFullName(fullName);
        employee.setPost(post);
        employee.setExperience(Integer.valueOf(experience));
        employee.setPhone(phone);
        employee.setEmail(email);

        employeeDao.save(employee);
        return Response.ok().build();
    }

    @PUT
    @Path("/{employeeId}")
    public Response updateEmployee(
            @PathParam("employeeId") String employeeId,
            @DefaultValue("") @FormParam("fullName") String fullName,
            @DefaultValue("") @FormParam("post") String post,
            @DefaultValue("") @FormParam("experience") String experience,
            @DefaultValue("") @FormParam("phone") String phone,
            @DefaultValue("") @FormParam("email") String email) {
        Employee employee = employeeDao.get(Integer.valueOf(employeeId));
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Employee with id %s not found", employeeId)).build();
        }

        if (!fullName.isEmpty()) {
            employee.setFullName(fullName);
        }
        if (!post.isEmpty()) {
            employee.setPost(post);
        }
        if (!experience.isEmpty()) {
            employee.setExperience(Integer.valueOf(experience));
        }
        if (!phone.isEmpty()) {
            employee.setPhone(phone);
        }
        if (!email.isEmpty()) {
            employee.setEmail(email);
        }
        employeeDao.update(employee);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{employeeId}")
    public Response deleteClient(@PathParam("employeeId") String employeeId) {
        Employee employee = employeeDao.get(Integer.valueOf(employeeId));
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Employee with id %s not found", employeeId)).build();
        } else {
            employeeDao.delete(employee);
            return Response.ok().build();
        }
    }
}

package com.example.lab1.controller;

import com.example.lab1.DAO.ClientDao;
import com.example.lab1.DAO.EmployeeDao;
import com.example.lab1.DAO.OrderDao;
import com.example.lab1.models.Client;
import com.example.lab1.models.Employee;
import com.example.lab1.models.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/order")
public class OrderController {
    @EJB
    private OrderDao orderDao;
    @EJB
    private ClientDao clientDao;
    @EJB
    private EmployeeDao employeeDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // GET localhost:8080/order/
    public Response getOrders() throws JsonProcessingException {
        List<Order> orders = orderDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(orders))
                .build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("orderId") String orderId) throws JsonProcessingException {
        Order order = orderDao.get(Integer.valueOf(orderId));
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Client with id %s not found", orderId)).build();
        }
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(order))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewOrder(
            @FormParam("info") String info,
            @FormParam("term") String term,
            @FormParam("status") String status,
            @FormParam("clientId") String clientId,
            @FormParam("employeeId") String employeeId) {
        Order order = new Order();
        order.setInfo(info);
        order.setTerm(term);
        order.setStatus(status);

        Client client = clientDao.get(Integer.valueOf(clientId));
        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Client with id %s not found", clientId)).build();
        }
        else {
            order.setClient(client);
        }

        Employee employee = employeeDao.get(Integer.valueOf(employeeId));
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Employee with id %s not found", employeeId)).build();
        }
        else {
            order.setEmployee(employee);
        }

        orderDao.save(order);
        return Response.ok().build();
    }

    @PUT
    @Path("/{orderId}")
    public Response updateOrder(
            @PathParam("orderId") String orderId,
            @DefaultValue("") @FormParam("info") String info,
            @DefaultValue("") @FormParam("term") String term,
            @DefaultValue("") @FormParam("status") String status) {
        Order order = orderDao.get(Integer.valueOf(orderId));
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("order with id %s not found", orderId)).build();
        }

        if (!info.isEmpty()) {
            order.setInfo(info);
        }
        if (!term.isEmpty()) {
            order.setTerm(term);
        }
        if (!status.isEmpty()) {
            order.setStatus(status);
        }
        orderDao.update(order);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{orderId}")
    public Response deleteClient(@PathParam("orderId") String orderId) {
        Order order = orderDao.get(Integer.valueOf(orderId));
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Order with id %s not found", orderId)).build();
        } else {
            orderDao.delete(order);
            return Response.ok().build();
        }
    }
}

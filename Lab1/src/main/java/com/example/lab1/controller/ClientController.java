package com.example.lab1.controller;

import com.example.lab1.DAO.ClientDao;
import com.example.lab1.models.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/clients")
public class ClientController {

    @EJB
    private ClientDao clientDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // GET localhost:8080/client/
    public Response getClients() throws JsonProcessingException {
        List<Client> clients = clientDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(clients))
                .build();
    }

    @GET
    @Path("/{clientId}")
    public Response getClientById(@PathParam("clientId") String clientId) throws JsonProcessingException {
        Client client = clientDao.get(Integer.valueOf(clientId));
        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Client with id %s not found", clientId)).build();
        }
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(client))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewClient(
            @FormParam("fullName") String fullName,
            @FormParam("phone") String phone,
            @FormParam("email") String email) {
        Client client = new Client();
        client.setFullName(fullName);
        client.setPhone(phone);
        client.setEmail(email);

        clientDao.save(client);
        return Response.ok().build();
    }

    @PUT
    @Path("/{clientId}")
    public Response updateClient(
            @PathParam("clientId") String clientId,
            @DefaultValue("") @FormParam("fullName") String fullName,
            @DefaultValue("") @FormParam("phone") String phone,
            @DefaultValue("") @FormParam("email") String email) {
        Client client = clientDao.get(Integer.valueOf(clientId));
        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Client with id %s not found", clientId)).build();
        }

        if (!fullName.isEmpty()) {
            client.setFullName(fullName);
        }
        if (!phone.isEmpty()) {
            client.setPhone(phone);
        }
        if (!email.isEmpty()) {
            client.setEmail(email);
        }
        clientDao.update(client);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{clientId}")
    public Response deleteClient(@PathParam("clientId") String clientId) {
        Client client = clientDao.get(Integer.valueOf(clientId));
        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Client with id %s not found", clientId)).build();
        } else {
            clientDao.delete(client);
            return Response.ok().build();
        }
    }
}

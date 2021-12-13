package com.example.lab2.controllers;

import com.example.lab2.models.Client;
import com.example.lab2.services.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping( value = "/", method = RequestMethod.GET, headers="accept=application/json") // GET localhost:8080/client/
    public ResponseEntity<Object> getClients(){
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok().body(clients);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // GET localhost:8080/order/
    public ModelAndView getClientsXSLT() throws JsonProcessingException {
        List<Client> clients = clientService.findAll();
        ModelAndView modelAndView = new ModelAndView("clients");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(clients)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping( value = "/{clientId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getClientById(@PathVariable("clientId") Integer clientId){
        Optional<Client> client = clientService.findById(clientId);
        if (!client.isPresent()) {
            return new ResponseEntity<Object>(String.format("Client with id %s not found", clientId),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(client.get());
    }

    @RequestMapping( value = "/", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewClient(@RequestBody Client client) {

        clientService.save(client);
        return ResponseEntity.ok().build();
    }

    @RequestMapping( value = "/{clientId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateClient(
            @PathVariable("clientId") Integer clientId,
            @RequestBody Client client
    ) {
        Optional<Client> findClient = clientService.findById(clientId);
        if (!findClient.isPresent()) {
            return new ResponseEntity<Object>(String.format("Employee with id %s not found", clientId),
                    HttpStatus.NOT_FOUND);
        }

        Client targetClient = findClient.get();

        if (!client.getFullName().isEmpty()) {
            targetClient.setFullName(client.getFullName());
        }
        if (!client.getPhone().isEmpty()) {
            targetClient.setPhone(client.getPhone());
        }
        if (!client.getEmail().isEmpty()) {
            targetClient.setEmail(client.getEmail());
        }
        clientService.save(targetClient);
        return ResponseEntity.ok().build();
    }

    @RequestMapping( value = "/{clientId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteClient(@PathVariable("clientId") Integer clientId) {
        Optional<Client> findClient = clientService.findById(clientId);
        if (!findClient.isPresent()) {
            return new ResponseEntity<Object>(String.format("Order with id %s not found", clientId),
                    HttpStatus.NOT_FOUND);
        }
            clientService.delete(findClient.get());
            return ResponseEntity.ok().build();
    }
}

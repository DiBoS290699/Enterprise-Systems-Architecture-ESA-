package com.example.lab2.services;

import com.example.lab2.jms.EventListenerFactory;
import com.example.lab2.jms.Sender;
import com.example.lab2.models.Client;
import com.example.lab2.repositories.ClientRepository;
import com.example.lab2.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository
@Transactional
public class ClientServiceImpl implements ClientService{

    private final ClientRepository repository;

    private Sender sender;

    @Autowired
    public ClientServiceImpl(ClientRepository repository, EventListenerFactory factory, Sender send) {
        this.repository = repository;
        sender = send;
        sender.subscribe(factory.createEmailLoggerListener());
        sender.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Client client) {

        repository.save(client);
        sender.sendInsertEvent("Client", client);
    }

    @Override
    public void delete(Client client) {

        repository.delete(client);
        sender.sendDeleteEvent("Client", client);
    }
}

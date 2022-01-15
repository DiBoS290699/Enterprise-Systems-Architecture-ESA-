package com.example.lab2.services;

import com.example.lab2.jms.Sender;
import com.example.lab2.models.Client;
import com.example.lab2.repositories.ClientRepository;
import com.example.lab2.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.util.List;
import java.util.Optional;

@Service
@Repository
@Transactional
public class ClientServiceImpl implements ClientService{

    private final ClientRepository repository;

    private Sender sender;

    @Autowired
    public ClientServiceImpl(ClientRepository repository, JmsTemplate jmsTemplate) throws JMSException {
        this.repository = repository;
        sender = new Sender(jmsTemplate);
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

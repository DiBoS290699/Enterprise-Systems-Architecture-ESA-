package com.example.lab2.services;

import com.example.lab2.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<Client> findById(Integer id);
    List<Client> findAll();
    void save(Client client);
    void delete(Client client);
}

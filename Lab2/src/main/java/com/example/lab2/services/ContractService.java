package com.example.lab2.services;

import com.example.lab2.models.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {
    Optional<Contract> findById(Integer id);
    List<Contract> findAll();
    void save(Contract contract);
    void delete(Contract contract);

}

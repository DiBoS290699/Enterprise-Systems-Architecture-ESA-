package com.example.lab2.repositories;

import com.example.lab2.models.Contract;
import org.springframework.data.repository.CrudRepository;

public interface ContractRepository extends CrudRepository<Contract, Integer> {
}

package com.example.lab2.repositories;

import com.example.lab2.models.Employee;
import org.springframework.data.repository.CrudRepository;


public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}

package com.example.lab2.services;

import com.example.lab2.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findById(Integer id);
    List<Employee> findAll();
    void save(Employee employee);
    void delete(Employee employee);
}

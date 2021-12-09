package com.example.lab2.controllers;


import com.example.lab2.models.Employee;
import com.example.lab2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/", method = RequestMethod.GET) // GET localhost:8080/employee/
    public ResponseEntity<Object> getEmployees() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok().body(employees);
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
        Optional<Employee> employee = employeeService.findById(employeeId);
        if (!employee.isPresent()) {
            return new ResponseEntity<Object>(String.format("Employee with id %s not found", employeeId),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(employee.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewEmployee(@RequestBody Employee employee) {

        employeeService.save(employee);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEmployee(
            @PathVariable("employeeId") Integer employeeId,
            @RequestBody Employee employee
    ) {
        Optional<Employee> findEmployee = employeeService.findById(employeeId);
        if (!findEmployee.isPresent()) {
            return new ResponseEntity<Object>(String.format("Employee with id %s not found", employeeId),
                    HttpStatus.NOT_FOUND);
        }

        Employee targetEmployee = findEmployee.get();

        if (!employee.getFullName().isEmpty()) {
            targetEmployee.setFullName(employee.getFullName());
        }
        if (!employee.getPost().isEmpty()) {
            targetEmployee.setPost(employee.getPost());
        }
        if (employee.getExperience() != null && employee.getExperience() > 0) {
            targetEmployee.setExperience(employee.getExperience());
        }
        if (!employee.getPhone().isEmpty()) {
            targetEmployee.setPhone(employee.getPhone());
        }
        if (!employee.getEmail().isEmpty()) {
            targetEmployee.setEmail(employee.getEmail());
        }
        employeeService.save(targetEmployee);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployee(@PathVariable("employeeId") Integer employeeId) {
        Optional<Employee> findEmployee = employeeService.findById(employeeId);
        if (!findEmployee.isPresent()) {
            return new ResponseEntity<Object>(String.format("Order with id %s not found", employeeId),
                    HttpStatus.NOT_FOUND);
        }
        employeeService.delete(findEmployee.get());
        return ResponseEntity.ok().build();
    }
}

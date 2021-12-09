package com.example.lab2.controllers;

import com.example.lab2.models.Client;
import com.example.lab2.models.Contract;
import com.example.lab2.models.Employee;
import com.example.lab2.services.ClientService;
import com.example.lab2.services.ContractService;
import com.example.lab2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;


    @RequestMapping(value = "/", method = RequestMethod.GET) // GET localhost:8080/order/
    public ResponseEntity<Object> getOrders() {
        List<Contract> orders = contractService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrderById(@PathVariable("orderId") Integer orderId) {
        Optional<Contract> order = contractService.findById(orderId);
        if (!order.isPresent()) {
            return new ResponseEntity<Object>(String.format("Order with id %s not found", orderId), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(order.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewOrder(
            @RequestBody Contract order,
            @RequestParam Integer clientId,
            @RequestParam Integer employeeId) {

        Optional<Client> client = clientService.findById(clientId);
        if (!client.isPresent()) {
            return new ResponseEntity<Object>(String.format("Client with id %s not found", clientId), HttpStatus.NOT_FOUND);
        }
        order.setClient(client.get());

        Optional<Employee> employee = employeeService.findById(employeeId);
        if (!employee.isPresent()) {
            return new ResponseEntity<Object>(String.format("Employee with id %s not found", employeeId), HttpStatus.NOT_FOUND);
        }
        order.setEmployee(employee.get());

        contractService.save(order);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateOrder(
            @PathVariable("orderId") Integer orderId,
            @RequestParam(defaultValue = "-1") Integer clientId,
            @RequestParam(defaultValue = "-1") Integer employeeId,
            @RequestBody Contract order
            ) {
        Optional<Contract> findOrder = contractService.findById(orderId);
        if (findOrder.isPresent()) {
            return new ResponseEntity<Object>(String.format("Order with id %s not found", orderId), HttpStatus.NOT_FOUND);
        }

        Contract targetOrder = findOrder.get();

        if (!order.getInfo().isEmpty()) {
            targetOrder.setInfo(order.getInfo());
        }
        if (!order.getTerm().isEmpty()) {
            targetOrder.setTerm(order.getTerm());
        }
        if (!order.getStatus().isEmpty()) {
            targetOrder.setStatus(order.getStatus());
        }

        Optional<Client> client = clientService.findById(clientId);
        if (client.isPresent() && clientId > 0) {
            return new ResponseEntity<Object>(String.format("Client with id %s not found", clientId), HttpStatus.NOT_FOUND);
        }
        targetOrder.setClient(client.get());

        Optional<Employee> employee = employeeService.findById(employeeId);
        if (employee.isPresent() && employeeId > 0) {
            return new ResponseEntity<Object>(String.format("Employee with id %s not found", employeeId), HttpStatus.NOT_FOUND);
        }
        targetOrder.setEmployee(employee.get());
        contractService.save(targetOrder);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteOrder(@PathVariable("orderId") Integer orderId) {
        Optional<Contract> findOrder = contractService.findById(orderId);
        if (!findOrder.isPresent()) {
            return new ResponseEntity<Object>(String.format("Order with id %s not found", orderId), HttpStatus.NOT_FOUND);
        }
        contractService.delete(findOrder.get());
        return ResponseEntity.ok().build();
    }
}

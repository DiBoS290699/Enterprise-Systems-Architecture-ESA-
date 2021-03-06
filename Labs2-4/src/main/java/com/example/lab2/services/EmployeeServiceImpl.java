package com.example.lab2.services;

import com.example.lab2.jms.Sender;
import com.example.lab2.models.Employee;
import com.example.lab2.repositories.EmployeeRepository;
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
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private Sender sender;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, JmsTemplate jmsTemplate) throws JMSException {
        this.repository = repository;
        sender = new Sender(jmsTemplate);
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Employee employee) {
        repository.save(employee);
        sender.sendInsertEvent("Employee", employee);
    }

    @Override
    public void delete(Employee employee) {
        repository.delete(employee);
        sender.sendDeleteEvent("Employee", employee);
    }

}

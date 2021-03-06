package com.example.lab2.services;

import com.example.lab2.jms.Sender;
import com.example.lab2.models.Contract;
import com.example.lab2.repositories.ContractRepository;
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
public class ContractServiceImpl implements ContractService{

    private final ContractRepository repository;

    private Sender sender;

    @Autowired
    public ContractServiceImpl(ContractRepository repository, JmsTemplate jmsTemplate) throws JMSException {
        this.repository = repository;
        sender = new Sender(jmsTemplate);
    }

    @Override
    public Optional<Contract> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Contract> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Contract contract) {
        repository.save(contract);
        sender.sendInsertEvent("Contract", contract);
    }

    @Override
    public void delete(Contract contract) {
        repository.delete(contract);
        sender.sendDeleteEvent("Contract", contract);
    }
}

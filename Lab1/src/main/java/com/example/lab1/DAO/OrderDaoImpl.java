package com.example.lab1.DAO;

import com.example.lab1.models.Contract;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class OrderDaoImpl implements OrderDao{

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public Contract get(Integer id) {
        return em.find(Contract.class, id);
    }

    @Override
    public List<Contract> getAll() {
        TypedQuery<Contract> query = em.createNamedQuery("Order.getAll", Contract.class);
        List<Contract> result = query.getResultList();
        return result;
    }

    @Override
    public void save(Contract order) {
        em.persist(order);
    }

    @Override
    public void update(Contract order) {
        em.merge(order);
    }

    @Override
    public void delete(Contract order) {
        em.remove(em.contains(order) ? order : em.merge(order));
    }
}

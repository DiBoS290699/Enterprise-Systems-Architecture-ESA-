package com.example.lab1.DAO;

import com.example.lab1.models.Order;

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
    public Order get(Integer id) {
        return em.find(Order.class, id);
    }

    @Override
    public List<Order> getAll() {
        TypedQuery<Order> query = em.createNamedQuery("Order.getAll", Order.class);
        List<Order> result = query.getResultList();
        return result;
    }

    @Override
    public void save(Order order) {
        em.persist(order);
    }

    @Override
    public void update(Order order) {
        em.merge(order);
    }

    @Override
    public void delete(Order order) {
        em.remove(em.contains(order) ? order : em.merge(order));
    }
}

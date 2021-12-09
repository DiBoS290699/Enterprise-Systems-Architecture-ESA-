package com.example.lab1.DAO;

import com.example.lab1.models.Employee;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class EmployeeDaoImpl implements EmployeeDao{

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public Employee get(Integer id) {
        return em.find(Employee.class, id);
    }

    @Override
    public List<Employee> getAll() {
        TypedQuery<Employee> query = em.createNamedQuery("Employee.getAll", Employee.class);
        List<Employee> result = query.getResultList();
        return result;
    }

    @Override
    public void save(Employee employee) {
        em.persist(employee);
    }

    @Override
    public void update(Employee employee) {
        em.merge(employee);
    }

    @Override
    public void delete(Employee employee) {
        em.remove(em.contains(employee) ? employee : em.merge(employee));
    }
}

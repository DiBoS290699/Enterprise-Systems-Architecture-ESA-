package com.example.lab1.DAO;

import com.example.lab1.models.Client;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

//    Просто для теста
    private final TypedQuery<Client> getAllQuery = em.createQuery("select c from Client c", Client.class);

    @Override
    public Client get(Integer id) {
        return em.find(Client.class, id);
    }

    @Override
    public List<Client> getAll() {
//        TypedQuery<Client> query = em.createNamedQuery("Client.getAll", Client.class);
        List<Client> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(Client client) {
        em.persist(client);
    }

    @Override
    public void update(Client client) {
        em.merge(client);
    }

    @Override
    public void delete(Client client) {
        em.remove(em.contains(client) ? client : em.merge(client));
    }
}

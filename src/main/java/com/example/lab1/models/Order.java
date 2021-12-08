package com.example.lab1.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order")
@Data
@NamedQueries({
        @NamedQuery(name = "Order.getAll", query = "select distinct o from Order o left join fetch o.client " +
                "left join fetch o.employee")
})
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "info")
    private String info;        // Информация заказа
    @Column(name = "term")
    private String term;          // Дата выполнения
    @Column(name = "status")
    private String status;      // Статус заказа

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId")
    private Employee employee;
}

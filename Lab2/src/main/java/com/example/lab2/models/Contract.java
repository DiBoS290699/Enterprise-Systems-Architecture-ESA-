package com.example.lab2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Contract")
@Data
public class Contract implements Serializable {
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

    @ManyToOne
    @JoinColumn(name = "clientId")
    @JsonIgnoreProperties({"orders"})
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    @JsonIgnoreProperties({"orders"})
    private Employee employee;
}

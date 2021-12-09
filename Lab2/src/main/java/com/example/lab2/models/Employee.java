package com.example.lab2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
public class Employee implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "post")
    private String post;            // Должность
    @Column(name = "experience")
    private Integer experience;     // опыт работы
    @Column(name = "phone")
    private String phone;           // Телефон
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "employee", orphanRemoval = true)
    @JsonIgnoreProperties({"client", "employee"})
    private List<Contract> orders;
}

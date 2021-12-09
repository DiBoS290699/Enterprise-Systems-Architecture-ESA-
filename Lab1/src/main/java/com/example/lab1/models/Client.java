package com.example.lab1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "client")
@Data
@NamedQueries({
        @NamedQuery(name = "Client.getAll", query = "select c from Client c")
})
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    @JsonIgnoreProperties({"client", "employee"})
    private List<Contract> orders;
}

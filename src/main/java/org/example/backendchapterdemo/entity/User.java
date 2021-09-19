package org.example.backendchapterdemo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;


    @NotEmpty
    @Column(unique = true)
    private String username;


    @NotEmpty
    @Column
    private String password;

    @Column
    @NotNull
    private Roles role = Roles.CUSTOMER;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Customer customer;

    public enum Roles {
        ADMIN,
        CUSTOMER
    }
}

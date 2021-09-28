package org.example.backendchapterdemo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Data
public class Customer {

    @Id
    @Column(name = "user_id", columnDefinition = "uuid")
    private UUID userId;

    @NotEmpty
    private String firstName;

    private String lastName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "user_id", columnDefinition = "uuid")
    private User user;
}
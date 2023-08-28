package com.DbSync.model.entities.serviceA;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Creator")
@NoArgsConstructor
@Data
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String resourceURI;
    private String name;
    private String role;

    public Creator(String resourceURI, String name, String role) {
        this.resourceURI = resourceURI;
        this.name = name;
        this.role = role;
    }
}

package com.DbSync.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SuperHero")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperHero {
    @Id
    private Long id;
    private String name;

}





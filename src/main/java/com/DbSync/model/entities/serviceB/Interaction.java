package com.DbSync.model.entities.serviceB;

import com.DbSync.model.entities.SuperHero;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Interaction")
@Data
@NoArgsConstructor
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hero_id") // Cambia esto según la columna correcta en tu base de datos
    private SuperHero hero;

    private String comicName;

    private String interactionHero;
}
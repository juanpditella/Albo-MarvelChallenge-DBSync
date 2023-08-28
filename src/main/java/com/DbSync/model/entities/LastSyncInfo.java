package com.DbSync.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LastSyncInfo")
@Data
@NoArgsConstructor

public class LastSyncInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime lastSyncDateTime; // Campo para almacenar la fecha y hora de la última sincronización
}
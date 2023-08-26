package ar.com.tdm.mock.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

package ar.com.tdm.mock.model.entities;

import ar.com.tdm.mock.model.entities.serviceA.CollaboratorHeroRelation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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





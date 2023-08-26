package ar.com.tdm.mock.model.entities.serviceA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CollaboratorHeroRelation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorHeroRelation {
    @Id
    private Long idSuperHero;
    private Long idCreator;
}

package ar.com.tdm.mock.model.entities.serviceA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorHeroRelationId implements Serializable {
    private Long idSuperHero;
    private Long idCreator;
}
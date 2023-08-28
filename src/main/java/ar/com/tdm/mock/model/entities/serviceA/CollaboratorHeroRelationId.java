package ar.com.tdm.mock.model.entities.serviceA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorHeroRelationId implements Serializable {
    private Long superHeroId;

    private Long idCreator;
}
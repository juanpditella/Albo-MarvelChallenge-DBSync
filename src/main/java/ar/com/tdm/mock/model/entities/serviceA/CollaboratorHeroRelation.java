package ar.com.tdm.mock.model.entities.serviceA;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "CollaboratorHeroRelation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorHeroRelation {
    @EmbeddedId
    private CollaboratorHeroRelationId id;
}
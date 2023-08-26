package ar.com.tdm.mock.repository.serviceA;

import ar.com.tdm.mock.model.entities.serviceA.CollaboratorHeroRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorHeroRepository extends JpaRepository<CollaboratorHeroRelation, Long> {
}

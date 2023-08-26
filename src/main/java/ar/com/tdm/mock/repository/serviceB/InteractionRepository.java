package ar.com.tdm.mock.repository.serviceB;

import ar.com.tdm.mock.model.entities.serviceB.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
}


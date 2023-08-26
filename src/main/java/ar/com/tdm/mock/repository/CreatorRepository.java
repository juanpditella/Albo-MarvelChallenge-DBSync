package ar.com.tdm.mock.repository;

import ar.com.tdm.mock.model.entities.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
}

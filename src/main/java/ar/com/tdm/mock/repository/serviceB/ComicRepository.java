package ar.com.tdm.mock.repository.serviceB;

import ar.com.tdm.mock.model.entities.serviceB.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Long> {
}

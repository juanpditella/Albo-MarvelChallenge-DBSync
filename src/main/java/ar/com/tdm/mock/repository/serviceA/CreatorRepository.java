package ar.com.tdm.mock.repository.serviceA;

import ar.com.tdm.mock.model.entities.serviceA.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {

    boolean existsByResourceURIAndNameAndRole(String resourceURI, String name, String role);

    Creator findByResourceURIAndNameAndRole(String resourceURI, String name, String role);
}

package com.DbSync.repository.serviceA;

import com.DbSync.model.entities.serviceA.CollaboratorHeroRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorHeroRepository extends JpaRepository<CollaboratorHeroRelation, Long> {
}

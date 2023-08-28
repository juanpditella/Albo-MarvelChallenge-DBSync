package com.DbSync.repository;

import com.DbSync.model.entities.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {
    SuperHero findByName(String superHeroName);
}

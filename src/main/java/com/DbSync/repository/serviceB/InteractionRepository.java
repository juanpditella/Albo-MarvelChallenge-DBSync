package com.DbSync.repository.serviceB;

import com.DbSync.model.entities.serviceB.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    Interaction findByComicNameAndHero_NameAndInteractionHero(String comicName, String heroName, String interactionHero);
}


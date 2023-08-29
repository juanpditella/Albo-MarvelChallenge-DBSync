package com.DbSync.services;


import com.DbSync.exceptions.HeroNotFoundException;
import com.DbSync.model.entities.LastSyncInfo;
import com.DbSync.model.entities.SuperHero;
import com.DbSync.model.entities.serviceA.CollaboratorHeroRelation;
import com.DbSync.model.entities.serviceA.Creator;
import com.DbSync.model.entities.serviceB.Interaction;
import com.DbSync.repository.LastSyncInfoRepository;
import com.DbSync.repository.SuperHeroRepository;
import com.DbSync.repository.serviceA.CollaboratorHeroRepository;
import com.DbSync.repository.serviceA.CreatorRepository;
import com.DbSync.repository.serviceB.InteractionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class DBSyncServiceImpl implements DBSyncService {

    private ApiMarvelClient apiMarvelClient;
    private CreatorRepository creatorRepository;
    private CollaboratorHeroRepository collaboratorHeroRepository;
    private SuperHeroRepository superHeroRepository;
    private LastSyncInfoRepository lastSyncInfoRepository;
    private InteractionRepository interactionRepository;


    public DBSyncServiceImpl(ApiMarvelClient apiMarvelClient, CreatorRepository creatorRepository,
                             CollaboratorHeroRepository collaboratorHeroRepository, SuperHeroRepository superHeroRepository,
                             LastSyncInfoRepository lastSyncInfoRepository,
                             InteractionRepository interactionRepository){

        this.apiMarvelClient = apiMarvelClient;
        this.creatorRepository=creatorRepository;
        this.collaboratorHeroRepository = collaboratorHeroRepository;
        this.superHeroRepository=superHeroRepository;
        this.lastSyncInfoRepository = lastSyncInfoRepository;
        this.interactionRepository=interactionRepository;
    }

    @Override
    @Transactional
    public void updateCollaborators(String superHeroName) throws HeroNotFoundException {
        log.info("Se comenzaran a cargar los colaboradores para el heroe "+superHeroName);
        SuperHero superHero = superHeroRepository.findByName(superHeroName);
        if (superHero == null) {
            try{
                Long idSH = apiMarvelClient.getSuperHeroIdByName(superHeroName);
                superHero = new SuperHero(idSH, superHeroName);
                this.superHeroRepository.save(superHero);
            } catch (HeroNotFoundException e) {
                throw new HeroNotFoundException("El heroe "+superHeroName+" no fue encontrado en la API de Marvel");
            }
        }

        if (superHero != null) {
            List<Creator> collaborators = apiMarvelClient.getCollaboratorsForHero(superHero.getId());
            log.info("Los colaboradores para el heroe "+superHeroName+" encontrados son: "+collaborators.toString());

            for (Creator collaborator : collaborators) {
                Creator existingCollaborator = creatorRepository.findByResourceURIAndNameAndRole(
                        collaborator.getResourceURI(), collaborator.getName(), collaborator.getRole());

                if (existingCollaborator == null) {
                    collaborator = creatorRepository.save(collaborator);
                } else {
                    collaborator = existingCollaborator;
                }

                CollaboratorHeroRelation relation = new CollaboratorHeroRelation(superHero.getId(), collaborator.getId());
                collaboratorHeroRepository.save(relation);
            }
        }
    }

    @Override
    @Transactional
    public void updateInteractedCharacters(String superHeroName) throws HeroNotFoundException {
        log.info("Se comenzaran a cargar las interacciones para el heroe "+superHeroName);

        SuperHero superHero = superHeroRepository.findByName(superHeroName);

        if (superHero == null) {
            try{
                Long idSH = apiMarvelClient.getSuperHeroIdByName(superHeroName);
                superHero = new SuperHero(idSH, superHeroName);
                this.superHeroRepository.save(superHero);
            } catch (HeroNotFoundException e) {
                throw new HeroNotFoundException("El heroe "+superHeroName+" no fue encontrado en la API de Marvel");
            }
        }

        if (superHero != null) {
            List<Interaction> interactions = apiMarvelClient.getInteractionsForSuperHero(superHero);
            log.info("Las interacciones para el heroe "+superHeroName+" encontradas son: "+interactions.toString());

            for (Interaction interaction : interactions) {
                Interaction existingInteraction = interactionRepository.findByComicNameAndHero_NameAndInteractionHero(
                        interaction.getComicName(), superHero.getName(), interaction.getInteractionHero());

                if (existingInteraction == null) {
                    interactionRepository.save(interaction);
                }
            }
        }
    }

    public void newSynchronization() {
        LastSyncInfo lastSyncInfo = new LastSyncInfo();
        LocalDateTime now = LocalDateTime.now();
        lastSyncInfo.setLastSyncDateTime(now);
        lastSyncInfoRepository.save(lastSyncInfo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        System.out.println(formattedDateTime);
    }
}


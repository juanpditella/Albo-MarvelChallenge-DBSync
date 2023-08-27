package ar.com.tdm.mock.services;

import ar.com.tdm.mock.model.entities.serviceA.CollaboratorHeroRelation;
import ar.com.tdm.mock.model.entities.serviceA.CollaboratorHeroRelationId;
import ar.com.tdm.mock.model.entities.serviceA.Creator;
import ar.com.tdm.mock.model.entities.LastSyncInfo;
import ar.com.tdm.mock.model.entities.SuperHero;
import ar.com.tdm.mock.model.entities.serviceB.Interaction;
import ar.com.tdm.mock.repository.serviceA.CreatorRepository;
import ar.com.tdm.mock.repository.serviceA.CollaboratorHeroRepository;
import ar.com.tdm.mock.repository.LastSyncInfoRepository;
import ar.com.tdm.mock.repository.SuperHeroRepository;
import ar.com.tdm.mock.repository.serviceB.InteractionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApiMarvelServiceImpl implements ApiMarvelService {

    private ApiMarvelClient apiMarvelClient;
    private CreatorRepository creatorRepository;
    private CollaboratorHeroRepository collaboratorHeroRepository;
    private SuperHeroRepository superHeroRepository;
    private LastSyncInfoRepository lastSyncInfoRepository;
    private InteractionRepository interactionRepository;


    public ApiMarvelServiceImpl(ApiMarvelClient apiMarvelClient, CreatorRepository creatorRepository,
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
    public void updateCollaborators(String superHeroName) {
        // Check if the SuperHero exists in the repository
        SuperHero superHero = superHeroRepository.findByName(superHeroName);
        if (superHero == null) {
            // If the SuperHero doesn't exist, create and save it
            Long idSH = apiMarvelClient.getSuperHeroIdByName(superHeroName);
            if (idSH!=null){
                superHero = new SuperHero(idSH, superHeroName);
                this.superHeroRepository.save(superHero);
            }
        }

        if (superHero != null) {

            List<Creator> collaborators = apiMarvelClient.getCollaboratorsForHero(superHero.getId());

            for (Creator collaborator : collaborators) {
                Creator existingCollaborator = creatorRepository.findByResourceURIAndNameAndRole(collaborator.getResourceURI(), collaborator.getName(), collaborator.getRole());

                if (existingCollaborator == null) {
                    collaborator = creatorRepository.save(collaborator);
                } else {
                    collaborator = existingCollaborator;
                }

                CollaboratorHeroRelation relation = new CollaboratorHeroRelation(new CollaboratorHeroRelationId(superHero.getId(),collaborator.getId()));
                collaboratorHeroRepository.save(relation);
            }
        }
    }

    @Override
    @Transactional
    public void updateInteractedCharacters(String superHeroName) {
        SuperHero superHero = superHeroRepository.findByName(superHeroName);
        if (superHero == null) {
            Long idSH = apiMarvelClient.getSuperHeroIdByName(superHeroName);
            if (idSH!=null){
                superHero = new SuperHero(idSH, superHeroName);
                this.superHeroRepository.save(superHero);
            }
        }

        if (superHero != null) {
            List<Interaction> interactions = apiMarvelClient.getInteractionsForSuperHero(superHero);

            for (Interaction interaction : interactions) {
                Interaction existingInteraction = interactionRepository.findByComicNameAndHero_NameAndInteractionHero(
                        interaction.getComicName(), superHero.getName(), interaction.getInteractionHero());

                if (existingInteraction == null) {
                    interactionRepository.save(interaction);
                }
            }
        }
    }

    public void newSinchronization(){
        LastSyncInfo lastSyncInfo = new LastSyncInfo();
        lastSyncInfo.setLastSyncDateTime(LocalDateTime.now());
        lastSyncInfoRepository.save(lastSyncInfo);
    }
}


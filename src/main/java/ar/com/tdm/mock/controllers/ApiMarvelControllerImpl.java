package ar.com.tdm.mock.controllers;

import ar.com.tdm.mock.services.ApiMarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Controller
public class ApiMarvelControllerImpl implements ApiMarvelController {
    private final ApiMarvelService apiMarvelService;
    private final List<String> heroesList;

    @Autowired
    public ApiMarvelControllerImpl(ApiMarvelService apiMarvelService, @Value("${heroes.list}") String heroes) {
        this.apiMarvelService = apiMarvelService;
        this.heroesList = Arrays.asList(heroes.split(","));
    }

    @PostConstruct
    public void initializeData() {
        updateData();
    }

    @Scheduled(cron = "0 0 2 * * ?") // Ejecutar a las 2 AM todos los días
    public void updateDataScheduled() {
        updateData();
    }

    public void updateData() {
        for (String hero : heroesList) {
            updateDataCharacter(hero);
        }
        this.apiMarvelService.newSynchronization();
    }

    private void updateDataCharacter(String character){
        apiMarvelService.updateCollaborators(character);
        apiMarvelService.updateInteractedCharacters(character);
    }
}






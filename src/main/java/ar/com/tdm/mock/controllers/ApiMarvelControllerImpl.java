package ar.com.tdm.mock.controllers;

import ar.com.tdm.mock.services.ApiMarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class ApiMarvelControllerImpl implements ApiMarvelController {
    private final ApiMarvelService apiMarvelService;

    @Autowired
    public ApiMarvelControllerImpl(ApiMarvelService apiMarvelService) {
        this.apiMarvelService = apiMarvelService;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 2 * * ?") // Ejecutar a las 2 AM todos los días
    public void updateData() {
        this.apiMarvelService.updateCollaborators("Iron Man");
        //this.apiMarvelService.updateCollaborators("Captain America");

    }

    private void updateDataCharacter(String character){
        apiMarvelService.updateCollaborators(character);
        //apiMarvelService.updateInteractedCharacters();
    }
}
package ar.com.tdm.mock.controllers;

import ar.com.tdm.mock.services.ApiMarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class ApiMarvelControllerImpl implements ApiMarvelController {
    private final ApiMarvelService apiService;

    @Autowired
    public ApiMarvelControllerImpl(ApiMarvelService apiService) {
        this.apiService = apiService;
    }

    @Scheduled(cron = "0 0 2 * * ?") // Ejecutar a las 2 AM todos los días
    public void updateData() {

    }

    private void updateDataCharacter(String character){
        apiService.updateCollaborators();
        apiService.updateInteractedCharacters();
    }
}
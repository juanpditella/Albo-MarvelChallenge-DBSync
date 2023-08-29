package com.DbSync.controllers;


import com.DbSync.exceptions.HeroNotFoundException;
import com.DbSync.services.DBSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
@Slf4j
@Controller
public class DBSyncControllerImpl implements DBSyncController {
    private final DBSyncService DBSyncService;
    private final List<String> heroesList;

    @Autowired
    public DBSyncControllerImpl(DBSyncService DBSyncService, @Value("${heroes.list}") String heroes) {
        this.DBSyncService = DBSyncService;
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
        log.info("Se comienza a cargar los datos en la base de datos");

        for (String hero : heroesList) {
            log.info("Se comienza a cargar los datos para el heroe "+ hero);
            updateDataCharacter(hero);
        }
        this.DBSyncService.newSynchronization();
    }

    private void updateDataCharacter(String character){
        try {
            DBSyncService.updateCollaborators(character);
            DBSyncService.updateInteractedCharacters(character);

        } catch (HeroNotFoundException e) {
            log.error("El heroe "+character+" no existe en la base de datos de Marvel. Su carga fue cancelada.");
        }
    }
}






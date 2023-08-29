package com.DbSync.services;


import com.DbSync.exceptions.HeroNotFoundException;
import com.DbSync.model.entities.SuperHero;
import com.DbSync.model.entities.serviceA.Creator;
import com.DbSync.model.entities.serviceB.Interaction;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ApiMarvelClient {

    @Value("${marvel.api.publicKey}")
    private String publicKey;

    @Value("${marvel.api.privateKey}")
    private String privateKey;
    private final String baseUrl = "https://gateway.marvel.com/v1/public";


    public List<Creator> getCollaboratorsForHero(Long heroID) {
        RestTemplate restTemplate = new RestTemplate();

        long timestamp = System.currentTimeMillis();
        String hash = generateHash(privateKey, publicKey, timestamp);

        String url = baseUrl + "/characters/" + heroID + "/comics" +
                "?ts=" + timestamp +
                "&apikey=" + publicKey +
                "&hash=" + hash;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            List<Creator> creatorSummaries = extractCreatorSummaries(response.getBody());
            return creatorSummaries;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    private List<Creator> extractCreatorSummaries(String responseBody) {
        List<Creator> creatorSummaries = new ArrayList<>();

        JSONObject responseObject = new JSONObject(responseBody);
        JSONArray resultsArray = responseObject.getJSONObject("data").getJSONArray("results");

        for (int comicIndex = 0; comicIndex < resultsArray.length(); comicIndex++) {
            JSONObject comicObject = resultsArray.getJSONObject(comicIndex);
            JSONArray creatorsArray = comicObject.getJSONObject("creators").getJSONArray("items");

            for (int i = 0; i < creatorsArray.length(); i++) {
                JSONObject creatorObject = creatorsArray.getJSONObject(i);
                String resourceURI = creatorObject.getString("resourceURI");
                String name = creatorObject.getString("name");
                String role = creatorObject.getString("role");
                if (role.equals("colorist") || role.equals("editor") || role.equals("writer")) {
                    Creator creatorSummary = new Creator(resourceURI, name, role);
                    creatorSummaries.add(creatorSummary);
                }
            }
        }

        return creatorSummaries;
    }

    public Long getSuperHeroIdByName(String superHeroName) throws HeroNotFoundException {
        RestTemplate restTemplate = new RestTemplate();

        long timestamp = System.currentTimeMillis();
        String hash = generateHash(privateKey, publicKey, timestamp);

        String url = baseUrl + "/characters" +
                "?name=" + superHeroName +
                "&ts=" + timestamp +
                "&apikey=" + publicKey +
                "&hash=" + hash;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            Long superHeroId = extractSuperHeroId(response.getBody());
            return superHeroId;
        } catch (Exception e) {
            throw new HeroNotFoundException("Los datos del heroe no son correctos.");
        }
    }

    private Long extractSuperHeroId(String responseBody) throws HeroNotFoundException {
        JSONObject responseObject = new JSONObject(responseBody);
        JSONArray resultsArray = responseObject.getJSONObject("data").getJSONArray("results");

        if (resultsArray.length() > 0) {
            JSONObject resultObject = resultsArray.getJSONObject(0);
            return resultObject.getLong("id");
        }
        else{
            throw new HeroNotFoundException("Los datos del heroe no son correctos.");
        }
    }

    // Método para generar el hash necesario para la autenticación
    public String generateHash(String privateKey, String publicKey, long ts) {
        try {
            String hashableString = ts + privateKey + publicKey;
            byte[] bytes = hashableString.getBytes(StandardCharsets.UTF_8);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(bytes);

            StringBuilder result = new StringBuilder();
            for (byte b : digest) {
                result.append(String.format("%02x", b));
            }

            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar la excepción de manera apropiada (por ejemplo, loguear el error)
            e.printStackTrace();
            return "";
        }
    }

    public List<Interaction> getInteractionsForSuperHero(SuperHero superHero) {
        Long superHeroId = null;
        try {
            superHeroId = getSuperHeroIdByName(superHero.getName());
        } catch (HeroNotFoundException e) {
            log.error("El heroe "+superHero+" no se encuentra el la API de Marvel");
            return new ArrayList<>();
        }

        RestTemplate restTemplate = new RestTemplate();
        long timestamp = System.currentTimeMillis();
        String hash = generateHash(privateKey, publicKey, timestamp);

        String url = baseUrl + "/characters/" + superHeroId + "/comics" +
                "?ts=" + timestamp +
                "&apikey=" + publicKey +
                "&hash=" + hash;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            List<Interaction> interactions = extractInteractions(response.getBody(), superHero);
            return interactions;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Interaction> extractInteractions(String responseBody, SuperHero superHero) {
        List<Interaction> interactions = new ArrayList<>();

        JSONObject responseObject = new JSONObject(responseBody);
        JSONArray resultsArray = responseObject.getJSONObject("data").getJSONArray("results");

        for (int comicIndex = 0; comicIndex < resultsArray.length(); comicIndex++) {
            JSONObject comicObject = resultsArray.getJSONObject(comicIndex);
            interactions.addAll(createInteractionsFromComic(comicObject, superHero));
        }
        return interactions;
    }

    private List<Interaction> createInteractionsFromComic(JSONObject comicObject, SuperHero superHero) {
        List<Interaction> interactionsList = new ArrayList<>();

        String comicTitle = comicObject.getString("title");

        JSONArray charactersArray = comicObject.getJSONObject("characters").getJSONArray("items");
        for (int charIndex = 0; charIndex < charactersArray.length(); charIndex++) {
            JSONObject characterObject = charactersArray.getJSONObject(charIndex);
            String characterName = characterObject.getString("name");
            if (!characterName.equals(superHero.getName())) {
                Interaction interaction = new Interaction();
                interaction.setComicName(comicTitle); // Set the comic title
                interaction.setInteractionHero(characterName); // Set the character name
                interaction.setHero(superHero); // Set the interaction hero
                interactionsList.add(interaction);
            }
        }
        return interactionsList;
    }
}


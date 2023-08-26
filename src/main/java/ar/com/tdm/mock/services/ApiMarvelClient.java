package ar.com.tdm.mock.services;

import ar.com.tdm.mock.model.entities.SuperHero;
import ar.com.tdm.mock.model.entities.serviceA.Creator;
import ar.com.tdm.mock.model.entities.serviceB.Comic;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiMarvelClient {

    private final String publicKey = "77ca3d98da341900b49f16a1c22d0167";
    private final String privateKey = "4848df92e1c9fc475401939e00b65a963266092a";
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
            System.out.println(creatorSummaries.toString());
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

    public Long getSuperHeroIdByName(String superHeroName) {
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
            e.printStackTrace();
            return null; // Return null in case of error
        }
    }

    private Long extractSuperHeroId(String responseBody) {
        JSONObject responseObject = new JSONObject(responseBody);
        JSONArray resultsArray = responseObject.getJSONObject("data").getJSONArray("results");

        if (resultsArray.length() > 0) {
            JSONObject resultObject = resultsArray.getJSONObject(0);
            return resultObject.getLong("id");
        }

        return null;
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

    public List<Comic> getComicsForCharacter(Long characterId) {
        RestTemplate restTemplate = new RestTemplate();

        long timestamp = System.currentTimeMillis();
        String hash = generateHash(privateKey, publicKey, timestamp);

        String url = baseUrl + "/characters/" + characterId + "/comics" +
                "?ts=" + timestamp +
                "&apikey=" + publicKey +
                "&hash=" + hash;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            List<Comic> comics = extractComics(response.getBody());
            return comics;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    private List<Comic> extractComics(String responseBody) {
        List<Comic> comics = new ArrayList<>();

        JSONObject responseObject = new JSONObject(responseBody);
        JSONArray resultsArray = responseObject.getJSONObject("data").getJSONArray("results");

        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject comicObject = resultsArray.getJSONObject(i);
            Long comicId = comicObject.getLong("id");
            String title = comicObject.getString("title");
            Comic comic = new Comic(comicId, title);
            comics.add(comic);
        }

        return comics;
    }
}
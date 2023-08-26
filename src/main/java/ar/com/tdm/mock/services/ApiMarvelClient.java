package ar.com.tdm.mock.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class ApiMarvelClient {

    //@Value("${marvel.api.publicKey}")
    private String publicKey = "77ca3d98da341900b49f16a1c22d0167";

    //@Value("${marvel.api.privateKey}")
    private String privateKey = "4848df92e1c9fc475401939e00b65a963266092a";

    private final String baseUrl = "https://gateway.marvel.com/v1/public";

    public ResponseEntity<String> getCollaboratorsForHero(String heroName) {
        RestTemplate restTemplate = new RestTemplate();

        //un ejemplo: http://gateway.marvel.com/v1/public/comics?ts=1&apikey=77ca3d98da341900b49f16a1c22d0167&hash=4e2bcb26e9d3842958c154f7856d85ba

        String url = baseUrl + "/characters/" + heroName + "/comics" +
                "?ts=" + System.currentTimeMillis() +
                "&apikey=" + publicKey +
                "&hash=" + generateHash(this.publicKey, this.privateKey, System.currentTimeMillis());


        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response;
    }

    // Método para generar el hash necesario para la autenticación
    public String generateHash(String privateKey, String publicKey, long ts) {
        try {
            String hashableString = String.valueOf(ts) + privateKey + publicKey;
            byte[] bytes = hashableString.getBytes(StandardCharsets.UTF_8);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(bytes);

            return new String(digest, StandardCharsets.UTF_8).toUpperCase();
        }
        catch(NoSuchAlgorithmException e){
            //log.info (No se pudo generar el hash)
            return "";
        }
    }
}
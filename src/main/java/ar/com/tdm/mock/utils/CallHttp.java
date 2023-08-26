package ar.com.tdm.mock.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallHttp {

	private final Logger log = LoggerFactory.getLogger(CallHttp.class);

	public static final String[] UNSAFE_CHARACTERS = { " ", ",", "#" };
	public static final String[] ENCODED_CHARACTERS = { "%20", "%2C", "%23" };
	public static final int TIMEOUT = 5000;

	public String llamadoHttp(URL url) throws IOException {

		url = new URL(encodeURL(url.toString()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		HttpURLConnection con = null;

		con = (HttpURLConnection) url.openConnection();

		con.setConnectTimeout(TIMEOUT);
		con.setReadTimeout(TIMEOUT);

		con.setRequestMethod("POST");
		con.setRequestProperty("cache-control", "no-cache");

		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "ISO-8859-1"));

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			log.error("Error " + url.toString() + " - Code: " + responseCode);
			return con.getContent().toString();
		}
	}

	public String llamadoHttpGet(URL url) throws IOException {

		url = new URL(encodeURL(url.toString()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		HttpURLConnection con = null;

		con = (HttpURLConnection) url.openConnection();

		con.setConnectTimeout(TIMEOUT);
		con.setReadTimeout(TIMEOUT);

		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");

		int responseCode = con.getResponseCode();
		
		if (responseCode == 200) {

			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "ISO-8859-1"));

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			log.error("Error " + url.toString() + " - Code: " + responseCode);
			return con.getContent().toString();
		}
	}
	
	public String llamadoHttps(URL url) throws IOException {

		url = new URL(encodeURL(url.toString()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		HttpsURLConnection con = null;

		con = (HttpsURLConnection) url.openConnection();

		con.setConnectTimeout(TIMEOUT);
		con.setReadTimeout(TIMEOUT);
		
		con.setDoOutput(true);
		con.setInstanceFollowRedirects(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("charset", "utf-8");

		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "ISO-8859-1"));

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			log.error("Error " + url.toString() + " - Code: " + responseCode);
			return con.getContent().toString();
		}
	}
	
	public static String encodeURL(String strUrl) {
		String encodedURL = strUrl;

		for (int i = 0; i < UNSAFE_CHARACTERS.length; i++) {
			encodedURL = encodedURL.replace(UNSAFE_CHARACTERS[i], ENCODED_CHARACTERS[i]);
		}

		return encodedURL;

	}

}

package com.charizard.minidex;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

//MiniDex aka "MidoriyaDex" - Simple PokeAPI Query for basic information, mainly used for CharizardBot.
// This is meant to be a test project that I created.
//Copyleft 2019 James, Meme Man 2019#0820

public class Main {
private static String pokeurl = "https://pokeapi.co/api/v2/pokemon/"; // Either use the official PokeAPI or use your own self hosted version.
private static String pkName = "";
    public static void main(String[] args) {
        JSONObject pokeResult = getPokemon("151");

        String pkTyp = "N/A";
        String pkTyp2 = "N/A";
        JSONObject typeArr = pokeResult.getJSONArray("types").getJSONObject(0).getJSONObject("type");
        String slot = typeArr.get("name").toString();
        pkTyp = slot.toString();
        String upper = pkTyp.substring(0, 1).toUpperCase();
        pkTyp = upper + pkTyp.substring(1, pkTyp.length());
     // 2nd type if applicable
        try {
        typeArr = pokeResult.getJSONArray("types").getJSONObject(1).getJSONObject("type");
        slot = typeArr.get("name").toString();
        pkTyp2 = slot.toString();
        upper = pkTyp2.substring(0, 1).toUpperCase();
        pkTyp2 = upper + pkTyp2.substring(1, pkTyp2.length());
        } catch (Exception e) {}
        
        //weight and height
        String weight = pokeResult.get("weight").toString();
        double pkWeight = (double) Integer.parseInt(weight) / 10; //formats it properly

        String height = pokeResult.get("height").toString();
        double pkHeight = (double) Integer.parseInt(height) / 10; //formats it properly

        //Base XP
        String baseXP = pokeResult.get("base_experience").toString();
        // Pokemon #
        String id = pokeResult.get("id").toString();

        String stats = "Main Type: " + pkTyp + "\nSecondary Type: " + pkTyp2 + "\nBase XP: " + baseXP + "\nHeight: " + pkHeight + " m\nWeight: " + pkWeight + 
        "kg\nNumber: " + id; // puts all the stats into one nice string
        pkName = pokeResult.get("name").toString();
        System.out.println(stats);

        //prints the pokemon name in case you wanted to enter a number.
        System.out.println("Pokemon Name:" + pkName);


        //sprite url for front_default
        JSONObject sprites = pokeResult.getJSONObject("sprites");
        String sprite = sprites.get("front_default").toString();
            System.out.println(sprite.toString());

        System.out.println(sprite);

    }
    public static String getPokemonName()
    {
        return pkName;
    }
    public static JSONObject getPokemon(String pokemon) {

        // make search request - using default locale of EN_US

        final String url = String.format(pokeurl + pokemon + "/");
        try {
            return get(url);
        } catch (Exception ignored) {
        }
        return null;
    }
    private static JSONObject get(String url) throws IOException, JSONException {
        HttpURLConnection connection = null;
        try {
            // Get request
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Handle failure
            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK && statusCode != HttpURLConnection.HTTP_CREATED) {
                String error = String.format("HTTP Code: '%1$s' from '%2$s'", statusCode, url);
                System.out.println(error);
                throw new ConnectException(error);
            }

            // Parse response
            return parser(connection);
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return new JSONObject("");
    }

    private static JSONObject parser(HttpURLConnection connection) throws JSONException {
        char[] buffer = new char[1024 * 4];
        int n;
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(connection.getInputStream());
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            StringWriter writer = new StringWriter();
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
            return new JSONObject(writer.toString());
        } catch (Exception IOException) {
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return new JSONObject("");
    }
}
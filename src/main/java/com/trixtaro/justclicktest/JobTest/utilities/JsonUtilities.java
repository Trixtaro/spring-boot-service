package com.trixtaro.justclicktest.JobTest.utilities;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtilities {

    /**
     * This method helps to save data as a JSON file
     * @param filename Name of the json file, including .json extension
     * @param json JSON array with all the information
     */
    public static void saveToJsonFile(String filename, JSONArray json){
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toJSONString());
            file.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get a JSON Array with all the information of a specified JSON file
     * @param filename Name of the json file to read, including .json extension
     * @return JSONArray with all the information of the file
     */
    public static JSONArray readJsonFile(String filename){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)){

            Object obj = jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            return jsonArray;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new JSONArray();
    }

}

package com.trixtaro.justclicktest.JobTest.controller;

import com.trixtaro.justclicktest.JobTest.entity.Link;
import com.trixtaro.justclicktest.JobTest.repository.LinkRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/link")
public class LinkController {

    private LinkRepository linkRepository;

    @Autowired
    public LinkController (LinkRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    @GetMapping("/{string}")
    public ResponseEntity get(@PathVariable String string, @RequestHeader Map<String, String> headers, HttpServletResponse response) throws IOException{

        List<Link> links = linkRepository.findByValue(string);

        if(links.size() > 0 && links.get(0).getRemainingCalls() > 0){

            Link currentLink = links.get(0);

            currentLink.setRemainingCalls(currentLink.getRemainingCalls() - 1);
            linkRepository.save(currentLink);

            JSONObject json = new JSONObject();

            headers.forEach((key, value) -> {
                json.put(key, value);
            });

            // Ading request to json file
            JSONArray jsonArray = readJsonFile("click.json");
            jsonArray.add(json);
            saveToJsonFile("click.json", jsonArray);

            response.sendRedirect(links.get(0).getUrl());
            return new ResponseEntity(HttpStatus.PERMANENT_REDIRECT);
        } else {
            return new ResponseEntity("4047 NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

    public void saveToJsonFile(String filename, JSONArray json){
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray readJsonFile(String filename){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)){

            Object obj = jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            return jsonArray;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONArray();
    }

}

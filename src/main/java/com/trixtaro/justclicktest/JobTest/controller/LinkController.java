package com.trixtaro.justclicktest.JobTest.controller;

import com.trixtaro.justclicktest.JobTest.entity.Link;
import com.trixtaro.justclicktest.JobTest.repository.LinkRepository;
import com.trixtaro.justclicktest.JobTest.utilities.JsonUtilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
    public ResponseEntity get(@PathVariable String string, @RequestHeader Map<String, String> headers, HttpServletRequest request ,HttpServletResponse response) throws IOException{

        // Adding a cookie for new user
        if (headers.get("cookie").indexOf("random_value") == -1)
            response.addCookie(
                    new Cookie(
                            "random_value",
                            String.valueOf(Math.random())
                    ));

        List<Link> links = linkRepository.findByValue(string);

        if(links.size() > 0 && links.get(0).getRemainingCalls() > 0){

            Link currentLink = links.get(0);

            currentLink.setRemainingCalls(currentLink.getRemainingCalls() - 1);
            linkRepository.save(currentLink);

            // Adding headers to JSON before store in click.json
            JSONObject json = new JSONObject();

            headers.forEach((key, value) -> {
                json.put(key, value);
            });

            // Adding request to json file
            JSONArray jsonArray = JsonUtilities.readJsonFile("click.json");
            jsonArray.add(json);
            JsonUtilities.saveToJsonFile("click.json", jsonArray);

            response.sendRedirect(links.get(0).getUrl());
            return new ResponseEntity(HttpStatus.PERMANENT_REDIRECT);
        } else {
            return new ResponseEntity("4047 NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

}

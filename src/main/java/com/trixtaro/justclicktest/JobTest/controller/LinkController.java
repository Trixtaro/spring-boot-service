package com.trixtaro.justclicktest.JobTest.controller;

import com.trixtaro.justclicktest.JobTest.entity.Link;
import com.trixtaro.justclicktest.JobTest.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/link")
public class LinkController {

    private LinkRepository linkRepository;

    @Autowired
    public LinkController (LinkRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    @GetMapping("/{string}")
    public ResponseEntity get(@PathVariable String string, HttpServletRequest request, HttpServletResponse response) throws IOException{

        List<Link> links = linkRepository.findByValue(string);

        if(links.size() > 0 && links.get(0).getRemainingCalls() > 0){

            Link currentLink = links.get(0);

            currentLink.setRemainingCalls(currentLink.getRemainingCalls() - 1);
            linkRepository.save(currentLink);

            response.sendRedirect(links.get(0).getUrl());
            return new ResponseEntity(HttpStatus.PERMANENT_REDIRECT);
        } else {
            return new ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

}

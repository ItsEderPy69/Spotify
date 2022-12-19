package com.Spotify.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Path;

@RestController
public class TestingController {
    @GetMapping("testing")
    public String getSpotifyToken(){
        return "ai dios salvame";
    }
}

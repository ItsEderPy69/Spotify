package com.Spotify.demo.controllers;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Service.SpotifyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyDataController {
    @Autowired
    private SpotifyDataService spotifyDataService;
    @GetMapping("/SpotifyData/search")
    public ResponseEntity<?> search(@RequestHeader(required = false) String Authorization,
                                    @RequestParam String value){
        try{
            ResponseEntity<Object> rs = spotifyDataService.Search(Authorization, value);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
    @GetMapping("/SpotifyData/album")
    public ResponseEntity<?> getAlbum(@RequestHeader(required = false) String Authorization,
                                    @RequestParam String id){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getAlbum(Authorization, id);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
}

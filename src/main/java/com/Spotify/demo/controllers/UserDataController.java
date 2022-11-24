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
public class UserDataController {
    @Autowired
    private SpotifyDataService spotifyDataService;
    @GetMapping("/UserData/me")
    public ResponseEntity<?> me(@RequestHeader String Authorization){
        try{
            return new ResponseEntity<>(spotifyDataService.getMe(Authorization), HttpStatus.OK);
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
    @GetMapping("/SpotifyData/me/album/guardados")
    public ResponseEntity<?> getAlbum(@RequestHeader String Authorization){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getAlbumGuardados(Authorization);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
}

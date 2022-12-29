package com.Spotify.demo.controllers;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Service.SpotifyDataService;
import com.Spotify.demo.model.Spotify.getGenres;
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
    @GetMapping(produces = { "application/json" }, value ="/SpotifyData/search")
    public ResponseEntity<?> search(@RequestHeader(required = false) String Authorization,
                                    @RequestParam(required = false) String value){
        try{
//            ResponseEntity<Object> rs = spotifyDataService.Search(Authorization, value);
//            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
            return new ResponseEntity<>(spotifyDataService.Search(Authorization, value), HttpStatus.OK);
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
    @GetMapping(produces = { "application/json" }, value ="/SpotifyData/album")
    public ResponseEntity<?> getAlbum(@RequestHeader(required = false) String Authorization,
                                    @RequestParam(required = false) String id){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getAlbum(Authorization, id);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }


    @GetMapping(produces = { "application/json" }, value ="/SpotifyData/GetGenres")
    public ResponseEntity<?> getGenres(@RequestHeader(required = false) String Authorization){
        try{
            //ResponseEntity<getGenres> rs = spotifyDataService.getGenres(Authorization);
            return new ResponseEntity<>(spotifyDataService.getGenres(Authorization).getBody(), HttpStatus.OK);
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }

    @GetMapping(produces = { "application/json" }, value ="/SpotifyData/artist")
    public ResponseEntity<?> getArtist(@RequestHeader(required = false) String Authorization, @RequestParam String artistID){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getArtist(Authorization,artistID);

            return new ResponseEntity<>(rs.getBody(), HttpStatus.OK);
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }

    @GetMapping(produces = { "application/json" }, value ="/SpotifyData/artist/top-tracks")
    public ResponseEntity<?> getArtistTopTracks(@RequestHeader(required = false) String Authorization, @RequestParam String artistID){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getArtistTopTracks(Authorization,artistID);

            return new ResponseEntity<>(rs.getBody(), HttpStatus.OK);
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }

    @GetMapping(produces = { "application/json" }, value ="/SpotifyData/artist/related")
    public ResponseEntity<?> getArtistRelated(@RequestHeader(required = false) String Authorization, @RequestParam String artistID){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getArtistRelated(Authorization,artistID);

            return new ResponseEntity<>(rs.getBody(), HttpStatus.OK);
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }

    @GetMapping(produces = { "application/json" }, value ="/SpotifyData/artist/albums")
    public ResponseEntity<?> getArtistAlbums(@RequestHeader(required = false) String Authorization, @RequestParam String artistID){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getArtistRelated(Authorization,artistID);

            return new ResponseEntity<>(rs.getBody(), HttpStatus.OK);
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
}

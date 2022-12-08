package com.Spotify.demo.controllers;
import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Service.SpotifyDataService;
import com.Spotify.demo.model.Spotify.AddTracksRequest;
import com.Spotify.demo.model.Spotify.CreatePlaylistRequest;
import com.Spotify.demo.model.Spotify.RemoveTracks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class UserDataController {
    @Autowired
    private SpotifyDataService spotifyDataService;
    @GetMapping(produces = { "application/json" }, value ="/UserData/me")
    public ResponseEntity<?> me(@RequestHeader String Authorization){
        try{
            return new ResponseEntity<>(spotifyDataService.getMe(Authorization), HttpStatus.OK);
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
    @GetMapping(produces = { "application/json" }, value ="/UserData/me/album/guardados")
    public ResponseEntity<?> getAlbum(@RequestHeader(required = false) String Authorization){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getAlbumGuardados(Authorization);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
    @PostMapping(produces = { "application/json" }, value ="/UserData/me/playlist")
    public ResponseEntity<?> createPlaylist(@RequestHeader(required = false)  String Authorization, @RequestBody CreatePlaylistRequest body){
        try{
            ResponseEntity<Object> rs = spotifyDataService.createPlaylist(Authorization, body);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
    @PostMapping(produces = { "application/json" }, value ="/UserData/me/playlist/add")
    public ResponseEntity<?> addMusicToPlaylist(@RequestHeader(required = false)  String Authorization, @RequestParam(required = false) String playlistID ,
                                                @RequestBody AddTracksRequest body){
        try{
            ResponseEntity<Object> rs = spotifyDataService.addTracksMusicToPlaylist(Authorization, playlistID, body);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }

    @DeleteMapping(produces = { "application/json" }, value ="/UserData/me/playlist/remove")
    public ResponseEntity<?> removeMusicInPlaylist(@RequestHeader(required = false)  String Authorization, @RequestParam(required = false) String playlistID ,
                                                @RequestBody RemoveTracks body){
        try{
            ResponseEntity<Object> rs = spotifyDataService.removeTracksInPlaylist(Authorization, playlistID, body);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }

    @GetMapping("/UserData/me/playlist")
    public ResponseEntity<?> getUsersPlaylists(@RequestHeader(required = false)  String Authorization){
        try{
            ResponseEntity<Object> rs = spotifyDataService.getUsersPlaylist(Authorization);
            return new ResponseEntity<>(rs.getBody(), rs.getStatusCode());
        }
        catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        }
    }
}

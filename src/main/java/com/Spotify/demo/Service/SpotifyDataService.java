package com.Spotify.demo.Service;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Security.TokenManager;
import com.Spotify.demo.SpotifyClient.EndPoints;
import com.Spotify.demo.SpotifyClient.SpotifyAuthManager;


import com.Spotify.demo.model.Spotify.AddTracksRequest;
import com.Spotify.demo.model.Spotify.CreatePlaylistRequest;

import com.Spotify.demo.model.Spotify.RemoveTracks;
import com.Spotify.demo.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import static com.Spotify.demo.Utilities.Funcion.fQuery;

@Service
public class SpotifyDataService {
    @Autowired
    private SpotifyAuthManager auth;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TokenManager tkManager;
    public Object getMe(@RequestHeader String token) throws SpotifyException {
            Usuario user = tkManager.IsAllowed(token);
            HttpHeaders headers = auth.getAuth(user);
            HttpEntity<String> entity =  new HttpEntity<>("headers", headers);
            String url = EndPoints.BASE_URL + EndPoints.ME;
            return (restTemplate.exchange(url, HttpMethod.GET, entity, Object.class)).getBody();
    }

    public <T> T getID(@RequestHeader String token, Class<T> classtype) throws SpotifyException {
        Usuario user = tkManager.IsAllowed(token);
        HttpHeaders headers = auth.getAuth(user);
        HttpEntity<String> entity =  new HttpEntity<>("headers", headers);
        String url = EndPoints.BASE_URL + EndPoints.ME;
        return (restTemplate.exchange(url, HttpMethod.GET, entity, classtype)).getBody();
    }

    public /*ResponseEntity<Object>*/Object Search(String token, String searchQuery) throws SpotifyException {
        Usuario user = tkManager.IsAllowed(token);
        HttpHeaders headers = auth.getAuth(user);
        if(searchQuery == null||searchQuery.isEmpty()){
            throw new SpotifyException("La busqueda no puede estar vacia", HttpStatus.BAD_REQUEST);
        } else if (searchQuery.contains("&")) {
            throw new SpotifyException("Busqueda mal formada", HttpStatus.BAD_REQUEST);
        }
        HttpEntity<String> entity = new HttpEntity<>("headers", headers);

//        ResponseEntity<Object> response = restTemplate.exchange(
//                EndPoints.SEARCH(fQuery(searchQuery)), HttpMethod.GET, entity,
//                Object.class);

        ResponseEntity<Object> response = restTemplate.exchange(
                EndPoints.SEARCH(fQuery(searchQuery)), HttpMethod.GET, entity,
                Object.class);
        return response.getBody();
    }

    public ResponseEntity<Object>  getAlbum(String token, String albumID) throws SpotifyException {

        Usuario user = tkManager.IsAllowed(token);
        if(albumID == null|| albumID.isEmpty()){
            throw new SpotifyException("El id del album no puede estar vacio", HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = auth.getAuth(user);
        HttpEntity<String> entity = new HttpEntity<>("headers", headers);
        String url = EndPoints.BASE_URL + EndPoints.ALBUM + albumID;
        ResponseEntity<Object> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        }catch (Exception ex){
            //response = new ResponseEntity<Object>("No se pudo encontrar el album,\n Detalles:" + ex.getMessage(),HttpStatus.NOT_FOUND);
            throw new SpotifyException("No se pudo encontrar el album,\n Detalles:" + ex.getMessage(),HttpStatus.NOT_FOUND);
        }

        return response;
    }

    public ResponseEntity<Object>  getAlbumGuardados(String token) throws SpotifyException {

        Usuario user = tkManager.IsAllowed(token);
        HttpHeaders headers = auth.getAuth(user);
        HttpEntity<String> entity = new HttpEntity<>("headers", headers);
        String url = EndPoints.BASE_URL + EndPoints.ALBUM_GUARDADOS;
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        return response;
    }

    public ResponseEntity<Object> addTracksMusicToPlaylist(String token, String PlaylistID, AddTracksRequest data) throws SpotifyException {

        Usuario user = tkManager.IsAllowed(token);
        HttpHeaders headers = auth.getAuth(user);
        if(data==null||data.getUris().isEmpty()){
            throw new SpotifyException("No se han agregado las playlist a añadir", HttpStatus.BAD_REQUEST);
        }
        HttpEntity<String> entity;
        String url = EndPoints.BASE_URL + EndPoints.PLAYLIST() + "/" + PlaylistID + EndPoints.TRACKS;
        ObjectMapper objectMapper = new ObjectMapper();
        String _body = "";
        try {
            _body =objectMapper.writeValueAsString(data);
        }catch (Exception ex){
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<>(_body, headers);
        ResponseEntity<Object> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        }catch (Exception ex){
            throw new SpotifyException("No se pudo agregar la musica a la playlist,\n Detalles:" + ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return response;
    }

    public ResponseEntity<Object> removeTracksInPlaylist(String token, String PlaylistID, RemoveTracks data) throws SpotifyException {

        Usuario user = tkManager.IsAllowed(token);
        HttpHeaders headers = auth.getAuth(user);
        if(data==null|| data.getTracks() == null || data.getTracks().isEmpty()){
            throw new SpotifyException("No se han seleccionado las playlist a remover", HttpStatus.BAD_REQUEST);
        }
        HttpEntity<String> entity;
        String url = EndPoints.BASE_URL + EndPoints.PLAYLIST() + "/" + PlaylistID + EndPoints.TRACKS;
        ObjectMapper objectMapper = new ObjectMapper();
        String _body = "";
        try {
            _body =objectMapper.writeValueAsString(data);
        }catch (Exception ex){
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<>(_body, headers);
        ResponseEntity<Object> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
        }catch (Exception ex){
            throw new SpotifyException("No se pudo agregar la musica a la playlist,\n Detalles:" + ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return response;
    }

    public ResponseEntity<Object> getUsersPlaylist(String token) throws SpotifyException {

        Usuario user = tkManager.IsAllowed(token);
        HttpHeaders headers = auth.getAuth(user);
        HttpEntity entity = new HttpEntity<>("headers", headers);
        String url = EndPoints.BASE_URL + EndPoints.USER + getUserID(user) + "/" + EndPoints.PLAYLIST();
        ResponseEntity<Object> response;
        response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        return response;
    }

    public ResponseEntity<Object> createPlaylist(String token, CreatePlaylistRequest body) throws SpotifyException {

        Usuario user = tkManager.IsAllowed(token);
        if(body.getName() == null|| body.getName().isEmpty()){
            throw new SpotifyException("El atributo name no puede estar vacio", HttpStatus.BAD_REQUEST);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = auth.getAuth(user);
        HttpEntity<String> entity;
        String _body = "";
        try {
            _body =objectMapper.writeValueAsString(body);
        }catch (Exception ex){

        }
        entity = new HttpEntity<>(_body, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = EndPoints.BASE_URL + EndPoints.PLAYLIST(getUserID(user));
        ResponseEntity<Object> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        }catch (Exception ex){
            //response = new ResponseEntity<Object>("No se crear la playlist,\n Detalles:" + ex.getMessage(),HttpStatus.NOT_FOUND);
            throw new SpotifyException("No se pudo crear la playlist,\n Detalles:" + ex.getMessage(),HttpStatus.NOT_FOUND);
        }

        return response;
    }
    public String getUserID(Usuario user) throws SpotifyException {
        ID id = getID(user.getToken(),ID.class);
        return id.getId();
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class ID{
        private String id;
    }


}


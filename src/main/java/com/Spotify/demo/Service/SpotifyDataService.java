package com.Spotify.demo.Service;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Security.TokenManager;
import com.Spotify.demo.SpotifyClient.EndPoints;
import com.Spotify.demo.SpotifyClient.SpotifyAuthManager;


import com.Spotify.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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

    public ResponseEntity<Object> Search(String token, String searchQuery) throws SpotifyException {
        Usuario user = tkManager.IsAllowed(token);
        HttpHeaders headers = auth.getAuth(user);
        if(searchQuery == null||searchQuery.isEmpty()){
            throw new SpotifyException("La busqueda no puede estar vacia", HttpStatus.BAD_REQUEST);
        } else if (searchQuery.contains("&")) {
            throw new SpotifyException("Busqueda mal formada", HttpStatus.BAD_REQUEST);
        }
        HttpEntity<String> entity = new HttpEntity<>("headers", headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                EndPoints.SEARCH(fQuery(searchQuery)), HttpMethod.GET, entity,
                Object.class);
        return response;
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
            response = new ResponseEntity<Object>("No se pudo encontrar el album,\n Detalles:" + ex.getMessage(),HttpStatus.NOT_FOUND);
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
}


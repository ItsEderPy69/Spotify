package com.Spotify.demo.controllers;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Repository.IUsuarioRepository;
import com.Spotify.demo.Security.TokenManager;
import com.Spotify.demo.Service.SpotifyAuthService;
import com.Spotify.demo.SpotifyClient.SpotifyAuthManager;
import com.Spotify.demo.model.SpotifyAuth;
import com.Spotify.demo.model.Usuario;
import com.Spotify.demo.model.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class LoginController {
    @Autowired
    private IUsuarioRepository usuario;
    @Autowired
    private TokenManager tkManager;
    @Autowired
    private SpotifyAuthManager spotifyAuth;
    @Autowired
    private SpotifyAuthService service;

    @PostMapping(produces = { "application/json" }, value = "/login")
    public ResponseEntity<?> createTodo(@RequestBody Usuario _user){
        try{
            Optional<Usuario> user = usuario.findByEmailAndPassword(_user.getEmail(), _user.getPassword());
            if(!user.isPresent()){
                throw new SpotifyException("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
            }else{
                tkManager.generateToken(user.get());
                user = usuario.findById(user.get().getId());
            }
            UsuarioDTO dto = new UsuarioDTO();
            dto.setToken(user.get().getToken());
            dto.setExpirationDate(user.get().getExpirationTokenDate());
            return  new ResponseEntity<UsuarioDTO>(dto, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(produces = { "application/json" }, value ="/login/getSpotifyLoginUrl")
    public ResponseEntity<?> SpotifyloginUrl(@RequestHeader String Authorization){
        try {
            Usuario user = tkManager.IsAllowed(Authorization);
            //String rs
            SpotifyAuthManager.SpotifyLogin rs = new SpotifyAuthManager.SpotifyLogin();
            rs.login_url = spotifyAuth.getAuthUrl(user);
            return new ResponseEntity<>(rs, HttpStatus.OK);
        }catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
        }
    }

    @GetMapping(produces = { "application/json" }, value ="/login/getSpotifyToken")
    public ResponseEntity<?> getSpotifyToken(@RequestHeader String Authorization){
        try {
            Usuario user = tkManager.IsAllowed(Authorization);
            //String rs = spotifyAuth.getAuthUrl(user);
            SpotifyAuth rs = service.get(user.getId());

            return new ResponseEntity<>(rs, HttpStatus.OK);
        }catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
        }
    }
}

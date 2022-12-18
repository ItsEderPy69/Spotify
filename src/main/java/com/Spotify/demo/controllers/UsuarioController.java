package com.Spotify.demo.controllers;


import com.Spotify.demo.Exception.SpotifyException;

import com.Spotify.demo.Security.TokenManager;
import com.Spotify.demo.Service.SpotifyDataService;
import com.Spotify.demo.Service.UsuarioService;
import com.Spotify.demo.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    ;

    @Autowired
    private TokenManager tkManager;



    @Autowired
    private UsuarioService usuarioService;
    @PostMapping(produces = { "application/json" }, value ="/usuario")
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario, @RequestHeader(required = false) String Authorization){
        try{
            tkManager.IsAllowed(Authorization);
            usuarioService.createUsuario(usuario);
            return  new ResponseEntity<>("Usuario Creado Correctamente", HttpStatus.OK);
        }catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

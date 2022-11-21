package com.Spotify.demo.controllers;

import com.Spotify.demo.Repository.IUsuarioRepository;
import com.Spotify.demo.Security.TokenManager;
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
    @PostMapping("/login")
    public ResponseEntity<?> createTodo(@RequestBody Usuario _user){
        try{
            Optional<Usuario> user = usuario.findByEmailAndPassword(_user.getEmail(), _user.getPassword());
            if(!user.isPresent()){
                throw new Exception("Credenciales incorrectas");
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
}

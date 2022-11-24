package com.Spotify.demo.controllers;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Repository.ITodoRepository;
import com.Spotify.demo.Security.TokenManager;
import com.Spotify.demo.SpotifyClient.SpotifyAuthManager;
import com.Spotify.demo.model.SpotifyAuth;
import com.Spotify.demo.model.TodoDTO;
import com.Spotify.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class controller {

    @Autowired
    private SpotifyAuthManager spotifyAuth;
    @Autowired
    private TokenManager tkManager;
//    @GetMapping("/todos")
//    public ResponseEntity<?> getAllTodos(){
//        List<TodoDTO> todos = todoRepo.findAll();
//        if(todos.size() > 0){
//            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("No se encontro nada", HttpStatus.NOT_FOUND);
//        }
//    };

    @GetMapping("/test")
    public ResponseEntity<?> tryLogin1(String token){
        try {
            Usuario user = tkManager.IsAllowed(token);
            //String rs = "";//spotifyAuth.getAuth();
            String rs = spotifyAuth.generateAuth(user).toString();
            return new ResponseEntity<>(rs, HttpStatus.OK);
        }catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
        }
    }

    @GetMapping("/getSpotifyLoginUrl")
    public ResponseEntity<?> SpotifyloginUrl(@RequestHeader(required = false) String Authorization){
        try {
            Usuario user = tkManager.IsAllowed(Authorization);
            String rs = spotifyAuth.getAuthUrl(user);
            return new ResponseEntity<>(rs, HttpStatus.OK);
        }catch (SpotifyException ex){
            return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
        }
    }

//    @PostMapping("/todos")
//    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
//        try{
//
//            todo.setCreatedAt(new Date(System.currentTimeMillis()));
//            todoRepo.save(todo);
//            return  new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
//        }catch (Exception ex){
//            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
}

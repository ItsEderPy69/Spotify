package com.Spotify.demo.Service;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Repository.IUsuarioRepository;
import com.Spotify.demo.SpotifyClient.SpotifyAuthManager;
import com.Spotify.demo.model.Usuario;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImp implements UsuarioService{
    @Autowired
    private IUsuarioRepository usuarioRepo;
    @Autowired
    private SpotifyAuthService spotifyAuthService;

    @Override
    public void createUsuario(Usuario _user) throws SpotifyException {
        Optional<Usuario> user = usuarioRepo.findByEmail(_user.getEmail());
        if(user.isPresent()){
            throw new SpotifyException("Usuario ya existe", HttpStatus.BAD_REQUEST);
        }else{
            _user.setCreatedAt(new Date(System.currentTimeMillis()));
            usuarioRepo.save(_user);
        }
    }


    @Override
    public void crearPredeterminado(){
        List<Usuario> listaUsuario = usuarioRepo.findAll();
        if(listaUsuario.isEmpty()){
            Usuario user = new Usuario();
            user.setEmail("a@a.com");
            user.setPassword("b");
            usuarioRepo.save(user);
        };
    }

    public Usuario get(String id){
        Optional<Usuario> OptUser = usuarioRepo.findById(id);

        if(OptUser.isPresent()){
            Usuario user = OptUser.get();
            user.setSpotifyAuth(spotifyAuthService.get(user.getId()));
            return user;
        }else{
            return new Usuario();
        }
    }
    public Usuario getUsuarioByToken(String token){
        Optional<Usuario> OptUser = usuarioRepo.findByToken(token);

        if(OptUser.isPresent()){
            Usuario user = OptUser.get();
            try {
                user.setSpotifyAuth(spotifyAuthService.get(user.getId()));
            }catch (Exception ex){

            }
            return user;
        }else{
            return new Usuario();
        }
    }
}

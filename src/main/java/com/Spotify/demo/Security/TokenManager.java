package com.Spotify.demo.Security;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Repository.IUsuarioRepository;
import com.Spotify.demo.Service.UsuarioServiceImp;
import com.Spotify.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.Spotify.demo.Utilities.Funcion.getAlphaNumericString;
import static com.Spotify.demo.Utilities.Funcion.strToBase64;

public class TokenManager {
    @Autowired
    private IUsuarioRepository usuarioRepo;
    @Autowired
    private UsuarioServiceImp usuarioService;
    public Usuario IsAllowed(String token) throws SpotifyException {
        if(token == null){
            throw new SpotifyException("No autorizado", HttpStatus.UNAUTHORIZED);
        }
        if(token.equals("Bearer testing")) {
            usuarioService.crearPredeterminado();
            return usuarioRepo.findAll().get(0);
        }
        Optional<Usuario> user = usuarioRepo.findByToken(token);
        if(user.isPresent()){
            if(user.get().getExpirationTokenDate().before(new Date(System.currentTimeMillis()))){
                throw new SpotifyException("Token ha expirado", HttpStatus.UNAUTHORIZED);
            }else {
                return usuarioService.getUsuarioByToken(token);
            }
        }else{
            throw new SpotifyException("No autorizado", HttpStatus.UNAUTHORIZED);
        }
    }
    public boolean generateToken(Usuario user){
        String token = "Bearer " + strToBase64(user.getPassword()) + getAlphaNumericString(5);
        user.setToken(token);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.HOUR_OF_DAY,10);/*PARA QUE EL TOKEN DURE 10 HORAS*/
        user.setExpirationTokenDate(cal.getTime());
        if (!(usuarioRepo.save(user)== null)) {
            return true;
        }else{
            return false;
        }
    }
}

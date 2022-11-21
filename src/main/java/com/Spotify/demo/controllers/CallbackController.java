package com.Spotify.demo.controllers;

import com.Spotify.demo.Repository.IUsuarioRepository;
import com.Spotify.demo.Service.SpotifyAuthService;
import com.Spotify.demo.Service.UsuarioServiceImp;
import com.Spotify.demo.SpotifyClient.SpotifyAuthManager;
import com.Spotify.demo.model.SpotifyAuth;
import com.Spotify.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;

import static com.Spotify.demo.SpotifyApplication.PendingAuth;

@Controller
public class CallbackController {

    @Autowired
    private SpotifyAuthManager spotifyAuthManager;

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private SpotifyAuthService authService;

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    @GetMapping(value = "/callback", produces = MediaType.TEXT_HTML_VALUE)
    public String showIndex(@RequestParam String code) {
        for(int x = PendingAuth.size() - 1 ; x >= 0  ; x-- ){
            Usuario user = usuarioServiceImp.get(PendingAuth.get(x));
            if(user.getSpotifyAuth()==null){
                user.setSpotifyAuth(new SpotifyAuth());
            }
            user.getSpotifyAuth().setCode(code);
            SpotifyAuth rs = spotifyAuthManager.generateAuth(user);
            if(rs.getAccess_token()!=null && (!rs.getAccess_token().isEmpty())){
                //user.getSpotifyAuth().setAccess_token(rs.getAccess_token());
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(System.currentTimeMillis()));
                cal.add(Calendar.SECOND, rs.getExpires_in());
                rs.setExpirationTokenDate(cal.getTime());
                rs.setUserID(user.getId());
                rs.setCode(code);
                rs.setCodeVerifier(user.getSpotifyAuth().getCodeVerifier());
                authService.save(rs);
                PendingAuth.remove(user.getId());
                //usuarioRepository.save(user);
                break;
            }
        }
        return "callback";
    }
}

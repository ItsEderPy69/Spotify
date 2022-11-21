package com.Spotify.demo.Service;

import com.Spotify.demo.Repository.ISpotifyAuthRepository;
import com.Spotify.demo.Utilities.PkceUtil;
import com.Spotify.demo.model.SpotifyAuth;
import com.Spotify.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotifyAuthService {
    @Autowired
    private ISpotifyAuthRepository spotifyAuthRepository;
    public void save(SpotifyAuth data){
        spotifyAuthRepository.save(data);
    }

    public void generatePCKE(Usuario user){
        if((user.getSpotifyAuth()==null)||(user.getSpotifyAuth().getCodeVerifier()==null)){
            SpotifyAuth data = new SpotifyAuth();
            data.setUserID(user.getId());
            try {
                data.setCodeVerifier(PkceUtil.generateCodeVerifier());
            }catch (Exception ex){
            }
            save(data);
        }
    }
    public SpotifyAuth get(String userID){
        return spotifyAuthRepository.findById(userID).get();
    }

}

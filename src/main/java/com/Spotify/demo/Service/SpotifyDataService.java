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
            HttpHeaders http = auth.getAuth(user);
            HttpEntity<String> entity =  new HttpEntity<>("headers",http);
            String url = EndPoints.BASE_URL + EndPoints.ME;
            return (restTemplate.exchange(url, HttpMethod.GET, entity, Object.class)).getBody();
        }
    }
//}

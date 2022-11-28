package com.Spotify.demo.SpotifyClient;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.Repository.IUsuarioRepository;
import com.Spotify.demo.Service.SpotifyAuthService;
import com.Spotify.demo.Service.SpotifyDataService;
import com.Spotify.demo.Utilities.PkceUtil;
import com.Spotify.demo.model.SpotifyAuth;
import com.Spotify.demo.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import static com.Spotify.demo.SpotifyApplication.PendingAuth;
import static com.Spotify.demo.Utilities.Funcion.strToBase64;

@Service
public class SpotifyAuthManager {
    @Autowired
    private SpotifyAuthService spotifyAuthService;
    public static final String CLIENT_ID = "83e56dc1d4434b2bbd88e6f332af990e";
    public static final String CLIENT_SECRET = "c563fe9d88824875a0c8d02da69757f2";
    public static final String AUTH_URL = "https://accounts.spotify.com/api/token";

    //public static final String REDIRECT_URI = "https://oauth.pstmn.io/v1/browser-callback";
    public static final String REDIRECT_URI = "http://localhost:8080/callback";

    //public static final String CODE = "AQDQ7AfmqYpCdpGH_7vUgAF3XkKOYqQ6Ysre2pxm5hqLWo74g2KWJiEYKQiDR1278vlnDzdxJ5FMV";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    public HttpHeaders getAuth(Usuario _user) throws SpotifyException {
        SpotifyAuth data = _user.getSpotifyAuth();
        if((data == null)||(data.getAccess_token()==null||data.getExpirationTokenDate().before(new Date(System.currentTimeMillis())))){
            refreshAuth(_user);
        };
        _user = usuarioRepository.findById(_user.getId()).get();
        data = _user.getSpotifyAuth();
        HttpHeaders rs = new HttpHeaders();
        rs.add("Authorization", data.getToken_type() + " " + data.getAccess_token());
        return rs;
    }

    private void refreshAuth(Usuario _user) throws SpotifyException {
        //_user.setSpotifyAuth(generateAuth());
        SpotifyAuth data = spotifyAuthService.get(_user.getId());
        //if((data == null)||(data.getRefreshToken().isEmpty())){
        if(data.getRefresh_token()== null) {
            data = generateAuth();
        }else{
            data = refreshAuthorizationToken(data);
        }
        data.setUserID(_user.getId());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.SECOND, data.getExpires_in());
        data.setExpirationTokenDate(cal.getTime());

        spotifyAuthService.save(data);

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date(System.currentTimeMillis()));
//        cal.add(Calendar.SECOND, _user.getSpotifyAuth().getExpires_in());
//        _user.getSpotifyAuth().setExpirationTokenDate(cal.getTime());
        usuarioRepository.save(_user);
    }

    public SpotifyAuth generateAuth(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String grant = strToBase64(CLIENT_ID + ":" + CLIENT_SECRET);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        headers.add("Authorization","Basic " + grant.replace("\n",""));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<SpotifyAuth> response = restTemplate.postForEntity(AUTH_URL, request, SpotifyAuth.class);
        return response.getBody();
    }

    public SpotifyAuth generateAuth(Usuario _user){
        try{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        SpotifyAuth data = _user.getSpotifyAuth();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", CLIENT_ID);
        map.add("grant_type", "authorization_code");
        map.add("code", data.getCode());
        map.add("redirect_uri", REDIRECT_URI);
        //map.add("code_verifier", generateCodeVerifier(_user.getEmail()));
            map.add("code_verifier", data.getCodeVerifier());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        //ResponseEntity<String> rsa = restTemplate.postForEntity(AUTH_URL, request, String.class);
        //String rsaS = rsa.getBody();
        ResponseEntity<SpotifyAuth> response = restTemplate.postForEntity(AUTH_URL, request, SpotifyAuth.class);
        return response.getBody();
        }catch (Exception ex){
            return new SpotifyAuth();
        }
    }

    //    public AuthorizationCodeRefreshRequest.Builder authorizationCodeRefresh() {
//        return new AuthorizationCodeRefreshRequest.Builder(clientId, clientSecret)
//                .setDefaults(httpManager, scheme, host, port)
//                .grant_type("refresh_token")
//                .refresh_token(refreshToken);
//    }
    public SpotifyAuth refreshAuthorizationToken(SpotifyAuth UserData) throws SpotifyException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("client_id", CLIENT_ID);
        map.add("refresh_token", UserData.getRefresh_token());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        try {
            ResponseEntity<SpotifyAuth> response = restTemplate.postForEntity(AUTH_URL, request, SpotifyAuth.class);
            SpotifyAuth rsData = response.getBody();
            UserData.setAccess_token(rsData.getAccess_token());
            return UserData;
        }catch (Exception ex){
            spotifyAuthService.delete(UserData);
            throw new SpotifyException("La sesión ha expirado, favor vuelva a iniciar a Spotify o intentelo de nuevo", HttpStatus.UNAUTHORIZED);
        }
    }




    public String getAuthUrl(Usuario _user){
        spotifyAuthService.generatePCKE(_user);
        if(!PendingAuth.contains(_user.getId())){
            PendingAuth.add(_user.getId());
        }


        try {
            return "https://accounts.spotify.com/en/authorize?client_id=" + CLIENT_ID
                    + "&response_type=code&redirect_uri=" + REDIRECT_URI
                    + "&code_challenge_method=S256&code_challenge=" + PkceUtil.generateCodeChallange(_user.getSpotifyAuth().getCodeVerifier())
                    + "&scope=ugc-image-upload,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,streaming,app-remote-control,user-read-email,user-read-private"
                    + ",playlist-read-collaborative,playlist-modify-public,playlist-read-private,playlist-modify-private,user-library-modify,user-library-read,user-top-read,user-read-playback-position,user-read-recently-played,user-follow-read,user-follow-modify";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}

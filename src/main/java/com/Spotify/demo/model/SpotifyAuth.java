package com.Spotify.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "spotifyAuth")
public class SpotifyAuth {
    @Id
    private String userID;
    private String access_token;
    private String token_type;
    private int expires_in;
    private Date expirationTokenDate;
    private String code;
    private String codeVerifier;
    private String refresh_token;
}

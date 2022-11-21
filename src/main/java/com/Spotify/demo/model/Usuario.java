package com.Spotify.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuario")
public class Usuario {
    @Id
    private String id;
    @NotNull(message = "email no puede estar vacio")
    private String email;
    @NotNull(message = "password no puede estar vacio")
    private String password;
    private String token;
    //private String code;
    //private String SpotifyToken;.
    @DBRef
    private SpotifyAuth spotifyAuth;
    private Date expirationTokenDate;
    private Date createdAt;
    private Date updatedAt;
}

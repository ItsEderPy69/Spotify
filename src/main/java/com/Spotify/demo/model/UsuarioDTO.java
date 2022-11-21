package com.Spotify.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioDTO {
    private String token;
    private Date expirationDate;
}

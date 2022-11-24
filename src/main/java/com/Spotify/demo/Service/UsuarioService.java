package com.Spotify.demo.Service;

import com.Spotify.demo.Exception.SpotifyException;
import com.Spotify.demo.model.Usuario;

public interface UsuarioService {
    void createUsuario(Usuario usuario) throws SpotifyException;
    void crearPredeterminado() throws SpotifyException;
}

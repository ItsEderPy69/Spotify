package com.Spotify.demo.Repository;

import com.Spotify.demo.model.TodoDTO;
import com.Spotify.demo.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends MongoRepository<Usuario, String> {
    public Optional<Usuario> findByToken(String token);
    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByEmailAndPassword(String email, String password);
}

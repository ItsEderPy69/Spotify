package com.Spotify.demo.Repository;

import com.Spotify.demo.model.SpotifyAuth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpotifyAuthRepository extends MongoRepository<SpotifyAuth, String> {

}

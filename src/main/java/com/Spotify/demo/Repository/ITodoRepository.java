package com.Spotify.demo.Repository;

import com.Spotify.demo.model.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITodoRepository extends MongoRepository<TodoDTO, String> {

}

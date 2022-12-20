package com.Spotify.demo.model.Spotify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "playlist")
public class CreatePlaylistRequest {
    private String name;
    private String description;

}

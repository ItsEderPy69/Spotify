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
@Document(collection = "playlistm")
public class AddTracksRequest {
    private ArrayList<String> uris;

//    {
//        "uris": [
//        "spotify:track:3IQKv4kEw4htOOLysL7Y88"
//    ]
//    }
}

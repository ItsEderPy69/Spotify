package com.Spotify.demo.model.Spotify;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)

public class getGenres {
    public ArrayList<String> genres;
}
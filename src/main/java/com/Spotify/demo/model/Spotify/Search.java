package com.Spotify.demo.model.Spotify;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */


import com.Spotify.demo.model.Spotify.Referencias.Albums;
import com.Spotify.demo.model.Spotify.Referencias.Artist;
import com.Spotify.demo.model.Spotify.Referencias.Playlists;
import com.Spotify.demo.model.Spotify.Referencias.Tracks;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Search{
    public Albums albums;
    public Artist artists;
    public Tracks tracks;
    public Playlists playlists;
}


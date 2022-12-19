package com.Spotify.demo.model.Spotify.Referencias;

import com.Spotify.demo.model.Spotify.Referencias.Album;
import com.Spotify.demo.model.Spotify.Referencias.Artist;
import com.Spotify.demo.model.Spotify.Referencias.ExternalIds;
import com.Spotify.demo.model.Spotify.Referencias.ExternalUrls;

import java.util.ArrayList;

public class Track {
    public Album album;
    public ArrayList<Artist> artists;
    //public ArrayList<String> available_markets;
    public int disc_number;
    public int duration_ms;
    public boolean explicit;
    public ExternalIds external_ids;
    public ExternalUrls external_urls;
    public String href;
    public String id;
    public boolean is_local;
    public String name;
    public int popularity;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;
}

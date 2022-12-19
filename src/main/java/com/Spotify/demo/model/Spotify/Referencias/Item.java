package com.Spotify.demo.model.Spotify.Referencias;

import com.Spotify.demo.model.Spotify.Referencias.Track;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class Item {
    public String album_type;
    public ArrayList<Artist> artists;
    //public ArrayList<String> available_markets;
    public ExternalUrls external_urls;
    public String href;
    public String id;
    public ArrayList<Image> images;
    public String name;
    public String release_date;
    public String release_date_precision;
    public int total_tracks;
    public String type;
    public String uri;
    public Followers followers;
    public ArrayList<Object> genres;
    public int popularity;
    public Album album;
    public int disc_number;
    public int duration_ms;
    public boolean explicit;
    public ExternalIds external_ids;
    public boolean is_local;
    public String preview_url;
    public int track_number;
    public boolean collaborative;
    public String description;
    public Owner owner;
    public Object primary_color;
    @JsonProperty("public")
    public Object mypublic;
    public String snapshot_id;
    public Tracks tracks;
    public Date added_at;
    public Track track;
}

package com.Spotify.demo.model.Spotify;

import com.Spotify.demo.model.Spotify.Referencias.*;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

//public class Item{
//    public Date added_at;
//    public Track track;
//}
public class SavedTracks{
    public String href;
    public ArrayList<Item> items;
    public int limit;
    public Object next;
    public int offset;
    public Object previous;
    public int total;
}


package com.Spotify.demo.model.Spotify.Referencias;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Actions {
    public boolean interrupting_playback;
    public boolean pausing;
    public boolean resuming;
    public boolean seeking;
    public boolean skipping_next;
    public boolean skipping_prev;
    public boolean toggling_repeat_context;
    public boolean toggling_shuffle;
    public boolean toggling_repeat_track;
    public boolean transferring_playback;
}

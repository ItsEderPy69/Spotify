package com.Spotify.demo.model.Spotify;
import com.Spotify.demo.model.Spotify.Referencias.Actions;
import com.Spotify.demo.model.Spotify.Referencias.Context;
import com.Spotify.demo.model.Spotify.Referencias.Device;
import com.Spotify.demo.model.Spotify.Referencias.Item;

public class MePlayer{
    public Device device;
    public String repeat_state;
    public String shuffle_state;
    public Context context;
    public int timestamp;
    public int progress_ms;
    public boolean is_playing;
    public Item item;
    public String currently_playing_type;
    public Actions actions;
}



package com.Spotify.demo.model.Spotify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveTracks {
    public ArrayList<Track> tracks;
}
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
class Track{
    public String uri;
}


package com.Spotify.demo.model.Spotify;

import com.Spotify.demo.model.Spotify.Referencias.ExplicitContent;
import com.Spotify.demo.model.Spotify.Referencias.ExternalUrls;
import com.Spotify.demo.model.Spotify.Referencias.Followers;
import com.Spotify.demo.model.Spotify.Referencias.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Me{
        public String country;
        public String display_name;
        //public String email;
        public ExplicitContent explicit_content;
        public ExternalUrls external_urls;
        public Followers followers;
        public String href;
        public String id;
        public ArrayList<Image> images;
        public String product;
        public String type;
        public String uri;
}


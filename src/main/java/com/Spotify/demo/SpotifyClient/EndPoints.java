package com.Spotify.demo.SpotifyClient;




public class EndPoints {
    public static String BASE_URL = "https://api.spotify.com/v1/";
    public static String ME = "me";
    public static String ALBUM = "albums/";
    public static final String ALBUM_GUARDADOS = "https://api.spotify.com/v1/me/albums?limit=50";

    public static final String SEARCH (String SearchString){
        return "https://api.spotify.com/v1/search?q=" + SearchString + "&type=album,artist,playlist,track&limit=1";
    };
}

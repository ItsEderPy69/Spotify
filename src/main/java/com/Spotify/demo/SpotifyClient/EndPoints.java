package com.Spotify.demo.SpotifyClient;


public class EndPoints {
    public static String BASE_URL = "https://api.spotify.com/v1/";

    public static final String SAVED_TRACKS = "me/tracks?limit=50";
    public static String ME = "me";
    public static String ALBUM = "albums/";
    //public static String PLAYLIST = /*aqui se espera el user ID*/"/playlists";
    public static final String ALBUM_GUARDADOS = "me/albums?limit=50";

    public static final String USER = "users/";
    public static final String SEARCH (String SearchString){
        return "https://api.spotify.com/v1/search?q=" + SearchString + "&type=album,artist,playlist,track&limit=1";
    };
    public static final String PLAYLIST(String userID){
        return USER + userID + "/" + PLAYLIST();
    };

    public static final String PLAYLIST(){
        return "playlists";
    };

    public static final String TRACKS =  "/tracks";

    public static final String FOLLOWPLAYLIST(String playlistID){
        return PLAYLIST() +"/"+ playlistID + "/" + "followers";
    };
}

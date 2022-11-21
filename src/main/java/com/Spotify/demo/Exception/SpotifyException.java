package com.Spotify.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SpotifyException extends Exception {
    public SpotifyException(String message, HttpStatus _statusCode){
        super(message);
        this.statusCode = _statusCode;
    }
    private HttpStatus statusCode;
    public HttpStatus getStatusCode(){
        return statusCode;
    }

    private class Message{
        public Message(String _error_message){
            error_message = _error_message;
        }
        private String error_message;
    }
}

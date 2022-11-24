package com.Spotify.demo.Exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SpotifyException extends Exception {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public SpotifyException(String message, HttpStatus _statusCode){
        super();
        this.statusCode = _statusCode;
    }

    private HttpStatus statusCode;
    public HttpStatus getStatusCode(){
        return statusCode;
    }

    @Override
    public String getMessage() {
        Message msg = new Message(super.getMessage());
        try {
            return objectMapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            return msg.error_message;
        }
    }

    private class Message{
        public Message(String _error_message){
            error_message = _error_message;
        }
        private String error_message;
    }
}

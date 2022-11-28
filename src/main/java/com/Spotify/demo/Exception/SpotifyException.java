package com.Spotify.demo.Exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SpotifyException extends Exception {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Message message;
    public SpotifyException(String message, HttpStatus _statusCode){
        super();
        this.statusCode = _statusCode;
        this.message = new Message(message);
    }

    private HttpStatus statusCode;
    public HttpStatus getStatusCode(){
        return statusCode;
    }

    @Override
    public String getMessage() {
        //Message msg = new Message(super.getMessage());
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            return message.error_message;
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Message{
//        public Message(String _error_message){
//            error_message = _error_message;
//        }
        private String error_message;
    }
}

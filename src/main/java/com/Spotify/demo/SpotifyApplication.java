package com.Spotify.demo;

import com.Spotify.demo.Security.TokenManager;
import com.Spotify.demo.model.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SpotifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyApplication.class, args);
	}

	public static List<String> PendingAuth = new ArrayList<>();

	@Bean
	public TokenManager getTokenManager(){return new TokenManager();}
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
}

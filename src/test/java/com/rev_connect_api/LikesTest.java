package com.rev_connect_api;

import java.io.IOException;
import java.net.http.HttpClient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LikesTest {
    ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;

    //Before each test, reset the database and Javalin app, and create new web client and object mapper
    @BeforeEach
    public void setUp() throws InterruptedException{
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[] {};
        app = SpringApplication.run(RevConnectApiApplication.class, args);
        Thread.sleep(500);
    }
    @AfterEach
    public void tearDown() throws InterruptedException{
        Thread.sleep(500);
        SpringApplication.exit(app);
    }


    @Test
    public void postLikeSuccessful() throws IOException, InterruptedException{
        
    }

}

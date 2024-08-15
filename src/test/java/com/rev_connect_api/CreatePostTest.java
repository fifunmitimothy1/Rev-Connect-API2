package com.rev_connect_api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.Post;

public class CreatePostTest {	
	ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;

    /**
     * Before every test, reset the database, restart the Javalin app, and create a new webClient and ObjectMapper
     * for interacting locally on the web.
     * @throws InterruptedException
     */
    @BeforeEach
    public void setUp() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[] {};
        app = SpringApplication.run(RevConnectApiApplication.class, args);
        Thread.sleep(500);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
    	Thread.sleep(500);
    	SpringApplication.exit(app);
    }
    

    /**
     * Sending an http request to POST localhost:8080/post with valid Post credentials
     * 
     * Expected Response:
     *  Status Code: 200
     *  Response Body: JSON representation of Post object
     */

     // sample UUID: cebfc8ff-182f-4ede-8c93-765375db59b5
    @Test
    public void createPostSuccessful() throws IOException, InterruptedException {
    	String json = "{\"postedBy\":9999,\"postText\": \"hello Post\",\"timePostedEpoch\": 1669947792}";
        HttpRequest postPostRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/post"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postPostRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);
        ObjectMapper om = new ObjectMapper();
        Post expectedResult = new Post(1, 9999, "hello Post", Long.valueOf(1669947792));
        Post actualResult = om.readValue(response.body().toString(), Post.class);
        Assertions.assertEquals(expectedResult, actualResult, "Expected="+expectedResult + ", Actual="+actualResult);
    }
    
    /**
     * Sending an http request to POST localhost:8080/post with empty Post
     * 
     * Expected Response:
     *  Status Code: 400
     */
    @Test
    public void createPostpostTextBlank() throws IOException, InterruptedException {
    	String json = "{\"postedBy\":9999,\"postText\": \"\",\"timePostedEpoch\": 1669947792}";
        HttpRequest postPostRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/post"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postPostRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Code was: " + status);
    }


    /**
     * Sending an http request to POST localhost:8080/post with Post length greater than 254
     * 
     * Expected Response:
     *  Status Code: 400
     *  Response Body: 
     */
    @Test
    public void createPostPostGreaterThan255() throws IOException, InterruptedException {
    	String json = "{\"postedBy\":9999,"
    			+ "\"postText\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\","
    			+ "\"timePostedEpoch\": 1669947792}";
        HttpRequest postPostRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/post"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postPostRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Code was: " + status);
    }

    /**
     * Sending an http request to POST localhost:8080/post with a user id that doesnt exist in db
     * 
     * Expected Response:
     * Status Code: 400
     */
    @Test
    public void createPostUserNotInDb() throws IOException, InterruptedException {
    	String json = "{\"postedBy\":5050,\"postText\": \"hello Post\",\"timePostedEpoch\": 1669947792}";
        HttpRequest postPostRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/post"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postPostRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Code was: " + status);
    }
}

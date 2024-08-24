package com.rev_connect_api.following;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.security.JwtUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test_states/followTestInit.sql")
public class FollowTest {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TestRestTemplate testRestTemplate;
  @Autowired
  private JwtUtil jwtUtil;

  @LocalServerPort
  private int port;

  private String serviceLocation, token;
  private ObjectMapper mapper;


  private final User userInitial, user2Initial, buisnessInitial, userFollowsUser2, user2FollowedByUser, userUnfollowsBuisness, buisnessNotFollowedByUser;

  public FollowTest() {
    // Create initial state of user
    userInitial = new User();
    userInitial.setUserId(1L);
    userInitial.setUsername("testuser1");
    userInitial.setPassword("$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO");
    userInitial.setEmail("test1@gmail.com");
    userInitial.setFirstName("test");
    userInitial.setLastName("tester");
    userInitial.setIsBusiness(false);
    // Create initial state of user2
    user2Initial = new User();
    user2Initial.setUserId(2L);
    user2Initial.setUsername("testuser2");
    user2Initial.setPassword("$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO");
    user2Initial.setEmail("test2@gmail.com");
    user2Initial.setFirstName("test");
    user2Initial.setLastName("tester");
    user2Initial.setIsBusiness(false);
    // Create initial state of buisness
    buisnessInitial = new User();
    buisnessInitial.setUserId(3L);
    buisnessInitial.setUsername("testuser3");
    buisnessInitial.setPassword("$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO");
    buisnessInitial.setEmail("test3@gmail.com");
    buisnessInitial.setFirstName("test");
    buisnessInitial.setLastName("tester");
    buisnessInitial.setIsBusiness(true);
    // Establish initil followings
    userInitial.setFollowing(Set.of(buisnessInitial));
    buisnessInitial.setFollowers(Set.of(userInitial));
    // Create goal states
    userFollowsUser2 = new User(userInitial);
    user2FollowedByUser = new User(user2Initial);
    userUnfollowsBuisness = new User(userInitial);
    buisnessNotFollowedByUser = new User(buisnessInitial);
    // Establish goal followings
    userFollowsUser2.setFollowing(Set.of(buisnessInitial,user2Initial));
    user2FollowedByUser.setFollowers(Set.of(userInitial));
    userUnfollowsBuisness.setFollowing(Set.of());
    buisnessNotFollowedByUser.setFollowers(Set.of());

  }

  @BeforeEach
    public void beforeEach() {

        serviceLocation = "http://localhost:" + port + "/api/follow";
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        token = jwtUtil.generateToken(userInitial.getUsername(), Set.of("ROLE_USER"));
    }

  // @Test
  // public void isFollowing() {
  //   Optional<User> user = userRepository.findByUserId(1L);
  //   if(user.isPresent()) {
  //     Assertions.assert(user.getFollowing().contains(new User(2L)));
  //   }
  // }

  @Test
  public void followUser() {
    //Generate HTTP request
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token);
    HttpEntity<PersonalProfile> requestEntity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation + "/" + user2Initial.getUserId(), HttpMethod.POST, requestEntity,String.class);

    HttpStatusCode statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

    //check server response

    //Verify database content
    assertEquals(userFollowsUser2, userRepository.getById(userInitial.getUserId()));
    assertEquals(user2FollowedByUser, userRepository.getById(user2Initial.getUserId()));

  }

  public void unfollowUser() {

  }

  @Test
  public void sanityTest() {
    
  }
  
  
}

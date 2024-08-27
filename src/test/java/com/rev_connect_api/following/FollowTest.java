package com.rev_connect_api.following;
import org.hibernate.Hibernate;
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
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rev_connect_api.dto.FollowRequestDTO;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.FollowRepository;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.security.JwtUtil;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test_states/followTestInit.sql")
public class FollowTest {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private FollowRepository followRepository;
  @Autowired
  private TestRestTemplate testRestTemplate;
  @Autowired
  private JwtUtil jwtUtil;

  @LocalServerPort
  private int port;

  private String serviceLocation, token;
  private ObjectMapper mapper;


  private final User userInitial, user2Initial, buisnessInitial, userFollowsUser2, user2FollowedByUser, userUnfollowsBuisness, buisnessNotFollowedByUser;
  private final FollowRequestDTO validFollowDTO, validUnfollowDTO;

  public FollowTest() {
    // Create initial state of user
    userInitial = new User();
    userInitial.setUserId(1L);
    userInitial.setUsername("testuser1");
    userInitial.setPassword("$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO");
    userInitial.setEmail("test1@mail.com");
    userInitial.setFirstName("test");
    userInitial.setLastName("tester");
    userInitial.setIsBusiness(false);
    // Create initial state of user2
    user2Initial = new User();
    user2Initial.setUserId(2L);
    user2Initial.setUsername("testuser2");
    user2Initial.setPassword("$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO");
    user2Initial.setEmail("test2@mail.com");
    user2Initial.setFirstName("test");
    user2Initial.setLastName("tester");
    user2Initial.setIsBusiness(false);
    // Create initial state of buisness
    buisnessInitial = new User();
    buisnessInitial.setUserId(3L);
    buisnessInitial.setUsername("testuser3");
    buisnessInitial.setPassword("$2a$10$PUYTs0ypfVJDNHkheYxqz.1vXx2LlH2pUPub9ipwW0t5ygo9gzQXO");
    buisnessInitial.setEmail("test3@mail.com");
    buisnessInitial.setFirstName("test");
    buisnessInitial.setLastName("tester");
    buisnessInitial.setIsBusiness(true);
    // Establish initil followings
    // userInitial.setFollowing(Set.of(buisnessInitial));
    // buisnessInitial.setFollowers(Set.of(userInitial));
    // Create goal states
    userFollowsUser2 = new User(userInitial);
    user2FollowedByUser = new User(user2Initial);
    userUnfollowsBuisness = new User(userInitial);
    buisnessNotFollowedByUser = new User(buisnessInitial);
    // Establish goal followings
    // userFollowsUser2.setFollowing(Set.of(buisnessInitial,user2Initial));
    // user2FollowedByUser.setFollowers(Set.of(userInitial));
    // userUnfollowsBuisness.setFollowing(Set.of());
    // buisnessNotFollowedByUser.setFollowers(Set.of());

    validFollowDTO = new FollowRequestDTO(2L,"FOLLOW");
    validUnfollowDTO = new FollowRequestDTO(3L,"UNFOLLOW");


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
    HttpEntity<FollowRequestDTO> requestEntity = new HttpEntity<>(validFollowDTO, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.POST, requestEntity,String.class);
    
    //check server response

    //check status code
    HttpStatusCode statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

    // Verify database content

    // check follow repository for updated records
    assert(followRepository.findUsersWhoUserFollows(userInitial).contains(user2Initial));
    assert(followRepository.findUsersWhoFollowUser(user2Initial).contains(userInitial));

    // check if user follows user2 and user2 is followed by user 1
    assert(userRepository.findById(userInitial.getUserId()).get().getFollowing().contains(user2Initial));
    assert(userRepository.findById(user2Initial.getUserId()).get().getFollowers().contains(userInitial));

  }

  @Test
  public void unfollowUser() {
    //Generate HTTP request
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + token);
    HttpEntity<FollowRequestDTO> requestEntity = new HttpEntity<>(validUnfollowDTO, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(serviceLocation, HttpMethod.POST, requestEntity,String.class);
    
    //check server response

    //check status code
    HttpStatusCode statusCode = response.getStatusCode();
    assertEquals(HttpStatus.OK, statusCode, "Expected status code 200. Actual result was " + statusCode.value());

    // Verify database content

    // check follow repository for updated records
    assert(!followRepository.findUsersWhoUserFollows(userInitial).contains(buisnessInitial));
    assert(!followRepository.findUsersWhoFollowUser(buisnessInitial).contains(userInitial));

    // check if user follows user2 and user2 is followed by user 1
    assert(!userRepository.findById(userInitial.getUserId()).get().getFollowing().contains(buisnessInitial));
    assert(!userRepository.findById(buisnessInitial.getUserId()).get().getFollowers().contains(userInitial));
  }

  @Test
  public void sanityTest() {
    
  }
  
  
}

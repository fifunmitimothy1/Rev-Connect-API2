package com.rev_connect_api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PartnerChannelControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testCreatePartnerChannel() throws Exception {
    String newChannelJson = "{\"businessUserId\":111,\"name\":\"New Channel\",\"url\":\"https://newchannel.com\"}";

    mockMvc.perform(post("/api/partner_channels")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newChannelJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("New Channel"))
        .andExpect(jsonPath("$.url").value("https://newchannel.com"));
  }

  @Test
  public void testGetPartnerChannelsByBusinessUserId() throws Exception {
    mockMvc.perform(get("/api/partner_channels")
        .param("businessUserId", "111")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Partner Channel 1"))
        .andExpect(jsonPath("$[0].url").value("https://www.partnerchannel1.com"));
  }

  @Test
  public void testUpdatePartnerChannel() throws Exception {
    String updatedChannelJson = "{\"businessUserId\":112,\"name\":\"Updated Channel\",\"url\":\"https://updatedchannel.com\"}";

    mockMvc.perform(patch("/api/partner_channels/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedChannelJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Channel"))
        .andExpect(jsonPath("$.url").value("https://updatedchannel.com"));
  }

  @Test
  public void testDeletePartnerChannel() throws Exception {
    mockMvc.perform(delete("/api/partner_channels/{id}", 1L))
        .andExpect(status().isNoContent());
  }
}

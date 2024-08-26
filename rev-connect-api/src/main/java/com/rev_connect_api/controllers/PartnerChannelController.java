package com.rev_connect_api.controllers;

import com.rev_connect_api.models.PartnerChannel;
import com.rev_connect_api.services.PartnerChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partner_channels")
public class PartnerChannelController {

  @Autowired
  private PartnerChannelService partnerChannelService;

  @PostMapping
  public ResponseEntity<PartnerChannel> createPartnerChannel(@RequestBody PartnerChannel partnerChannel) {
    PartnerChannel createdChannel = partnerChannelService.createPartnerChannel(
        partnerChannel.getBusinessUserId(),
        partnerChannel.getName(),
        partnerChannel.getUrl());
    return ResponseEntity.ok(createdChannel);
  }

  @GetMapping
  public ResponseEntity<List<PartnerChannel>> getPartnerChannelsByBusinessUserId(@RequestParam Long businessUserId) {
    List<PartnerChannel> channels = partnerChannelService.getPartnerChannelsByBusinessUserId(businessUserId);
    return ResponseEntity.ok(channels);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<PartnerChannel> updatePartnerChannel(
      @PathVariable Long id,
      @RequestBody PartnerChannel partnerChannel) {
    PartnerChannel updatedChannel = partnerChannelService.updatePartnerChannel(
        id,
        partnerChannel.getBusinessUserId(),
        partnerChannel.getName(),
        partnerChannel.getUrl());
    return ResponseEntity.ok(updatedChannel);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePartnerChannel(@PathVariable Long id) {
    partnerChannelService.deletePartnerChannel(id);
    return ResponseEntity.noContent().build();
  }
}

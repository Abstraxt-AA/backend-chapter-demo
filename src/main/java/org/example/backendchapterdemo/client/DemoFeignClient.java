package org.example.backendchapterdemo.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.backendchapterdemo.config.DemoFeignClientConfiguration;
import org.example.backendchapterdemo.util.ClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "demoFeignClient", url = "https://content-youtube.googleapis.com/",
        configuration = DemoFeignClientConfiguration.class)
public interface DemoFeignClient {

    @GetMapping("youtube/v3/playlistItems?part=" + ClientConstants.PART + "&playlistId={playlistId}&key=" +
            ClientConstants.API_KEY + "&maxResults=" + ClientConstants.MAX_RESULTS + "&fields=" + ClientConstants.FIELDS)
    JsonNode listPlaylistItems(@PathVariable String playlistId);
}

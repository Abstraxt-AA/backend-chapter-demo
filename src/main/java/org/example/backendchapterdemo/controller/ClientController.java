package org.example.backendchapterdemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.OkHttpClient;
import org.example.backendchapterdemo.client.DemoFeignClient;
import org.example.backendchapterdemo.client.RetrofitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final DemoFeignClient demoFeignClient;

    private final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://content-youtube.googleapis.com/")
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient.build())
            .build();

    private final RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);

    @Autowired
    public ClientController(DemoFeignClient demoFeignClient) {
        this.demoFeignClient = demoFeignClient;
    }

    @GetMapping("/retrofit/demo/{playlistId}")
    public ResponseEntity<JsonNode> demoRetrofit(@PathVariable String playlistId) throws IOException {
        return ResponseEntity.ok(retrofitClient.listPlaylistItems(playlistId).execute().body());
    }

    @GetMapping("/feign/demo/{playlistId}")
    public ResponseEntity<JsonNode> demoFeign(@PathVariable String playlistId) {
        return ResponseEntity.ok(demoFeignClient.listPlaylistItems(playlistId));
    }
}

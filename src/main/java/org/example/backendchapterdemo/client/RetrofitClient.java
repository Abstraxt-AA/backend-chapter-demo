package org.example.backendchapterdemo.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.backendchapterdemo.util.ClientConstants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitClient {

    @Headers({
            "X-Origin: https://explorer.apis.google.com",
            "X-Referer: https://explorer.apis.google.com",
            "Referer: https://content-youtube.googleapis" +
                    ".com/static/proxy.html?usegapi=1&jsh=m%3B%2F_%2Fscs%2Fapps" +
                    "-static%2F_%2Fjs%2Fk%3Doz.gapi.en_US.UYHeVG_mX5s.O%2Fam%3D" +
                    "AQ%2Fd%3D1%2Frs%3DAGLTcCPDcESMLF74mIvk5CKxuCjzYIf5XA%2Fm%3" +
                    "D__features__"
    })
    @GET("youtube/v3/playlistItems?part=" + ClientConstants.PART + "&key=" + ClientConstants.API_KEY +
            "&maxResults=" + ClientConstants.MAX_RESULTS + "&fields=" + ClientConstants.FIELDS)
    Call<JsonNode> listPlaylistItems(@Query("playlistId") String playlistId);
}

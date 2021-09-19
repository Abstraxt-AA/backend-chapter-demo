package org.example.backendchapterdemo.config;

import feign.RequestInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoFeignClientConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate
                .header("X-Origin", "https://explorer.apis.google.com")
                .header("X-Referer", "https://explorer.apis.google.com")
                .header("Referer", "https://content-youtube.googleapis" +
                        ".com/static/proxy.html?usegapi=1&jsh=m%3B%2F_%2Fscs%2Fapps" +
                        "-static%2F_%2Fjs%2Fk%3Doz.gapi.en_US.UYHeVG_mX5s.O%2Fam%3D" +
                        "AQ%2Fd%3D1%2Frs%3DAGLTcCPDcESMLF74mIvk5CKxuCjzYIf5XA%2Fm%3" +
                        "D__features__");
    }
}

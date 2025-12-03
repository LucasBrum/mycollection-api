package com.brum.mycollection.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "discogs.api")
@Getter
@Setter
public class DiscogsConfig {
  private String baseUrl;
  private String token;
  private String userAgent;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}

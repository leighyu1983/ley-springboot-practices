package com.ley.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Leigh Yu
 * @date 2020/2/16 19:41
 */
@Configuration
public class RestTemplateBean {
    @Bean
    public RestTemplate restTemplate() {
        CloseableHttpClient client = HttpClientBuilder.create()
                .setMaxConnTotal(500)
                .setMaxConnPerRoute(800)
                .build();

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(client);
        httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
        httpRequestFactory.setConnectTimeout(30 * 1000);
        httpRequestFactory.setReadTimeout(30 * 1000);
        return new RestTemplate(httpRequestFactory);
    }
}

package org.example.grip_demo.map.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchApiService {
    @Value("${naver.search.client.id}")
    private String clientId;

    @Value("${naver.search.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String searchLocal(String query) {
        String url = "https://openapi.naver.com/v1/search/local.json?query=" + query + "&display=10";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}

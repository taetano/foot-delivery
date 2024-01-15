package com.project.delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.delivery.dto.GetAllStoresResponseDto;
import com.project.delivery.dto.StoreDto;
import com.project.delivery.util.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JwtProvider jwtProvider;
    @Test
    void getAllStores() throws JsonProcessingException {
        String token = jwtProvider.createToken("username");
        String url = "http://localhost:" + port + "/api/stores/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNotNull();

        GetAllStoresResponseDto body = objectMapper.readValue(response.getBody(), GetAllStoresResponseDto.class) ;
        List<StoreDto> stores = body.stores();
        for (StoreDto dto : stores) {
            System.out.println(dto);
        }
    }
}
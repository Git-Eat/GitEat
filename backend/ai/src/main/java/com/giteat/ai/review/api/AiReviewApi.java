package com.giteat.ai.review.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class AiReviewApi {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://i12b108.p.ssafy.io/api/rest";

    public AiReviewApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateReview(int prId, int repoId) {


        return "";
    }
}

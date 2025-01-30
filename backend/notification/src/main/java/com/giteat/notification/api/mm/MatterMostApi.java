package com.giteat.notification.api.mm;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class MatterMostApi {
    private final RestTemplate restTemplate;
    private final String url = "https://meeting.ssafy.com/hooks/heishg3retgh7p61urhdfycuxc";

    public void sendNotification(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("text", message); // Mattermost 메시지

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("✅ Mattermost 알림 전송 성공");
        } else {
            System.out.println("❌ Mattermost 알림 전송 실패: " + response.getBody());
        }
    }

}

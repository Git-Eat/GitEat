package com.giteat.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
@Slf4j
public class TypeUtil {
    ObjectMapper objectMapper;

    /**
     * JSON 문자열을 Object로 변환하는 유틸리티 메서드
     */
    public Object convertJsonToObject(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Object.class);
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            return Collections.singletonMap("error", "JSON 변환 실패");
        }
    }
}

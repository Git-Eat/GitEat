package com.giteat.security.controller;

import com.giteat.security.dto.NotiDto;
import com.giteat.security.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/noti")
@AllArgsConstructor
@Slf4j
public class NotiController {
    private final ApiUtil apiUtl;

    @PostMapping("/regi")
    @Operation(summary = "MM URL 등록", description = "알림에 필요한 MM URL 등록")
    public ResponseEntity<?> saveNotiToken(
                                           @RequestBody NotiDto notiDto) {
        ResponseEntity<?> response = apiUtl.postApi("/noti/regi" , notiDto);

        return ResponseEntity.ok(response.getBody());
    }

}

package com.giteat.noti.controller;


import com.giteat.noti.dto.NotiDto;
import com.giteat.noti.service.NotiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/noti")
@AllArgsConstructor
public class NotiController {

    private NotiService notiService;

    /**
     * 알림 관련 mm 등록하는 함수
     * @param header
     * @param notiDto
     * @return
     */
    @PostMapping("/regi")
    @Operation(summary = "MM URL 등록", description = "알림에 필요한 MM URL 등록")
    public ResponseEntity<?> saveNotiToken(@RequestHeader(value = "Authorization") String header ,
                                           @RequestBody NotiDto notiDto) {
        String accessToken = header.split(" ")[1];
        int result = notiService.saveNotiToken(notiDto);

        return ResponseEntity.ok(result);
    }
}


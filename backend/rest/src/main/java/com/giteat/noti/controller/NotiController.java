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

    // URL 추가
    @PostMapping("/addurl")
    public ResponseEntity<?> addUrl(@RequestHeader(value = "Authorization") String header,
                                    @RequestBody NotiDto notiDto) {
        int result = notiService.addUrl(notiDto);
        return ResponseEntity.ok(result);
    }

    // URL 조회
    @GetMapping("/geturl/{repoId}/{userId}")
    public ResponseEntity<?> getUrl(@RequestHeader(value = "Authorization") String header,
                                    @PathVariable int repoId,
                                    @PathVariable int userId) {
        NotiDto result = notiService.getUrl(new NotiDto(repoId, userId, null));
        return ResponseEntity.ok(result);
    }

    // URL 삭제
    @DeleteMapping("/deleteurl/{repoId}/{userId}")
    public ResponseEntity<?> deleteUrl(@RequestHeader(value = "Authorization") String header,
                                       @PathVariable int repoId,
                                       @PathVariable int userId) {
        int result = notiService.deleteUrl(repoId , userId);
        return ResponseEntity.ok(result);
    }

    // URL 업데이트
    @PutMapping("/updateurl")
    public ResponseEntity<?> updateUrl(@RequestHeader(value = "Authorization") String header,
                                       @RequestBody NotiDto notiDto) {
        int result = notiService.updateUrl(notiDto);
        return ResponseEntity.ok(result);
    }
}


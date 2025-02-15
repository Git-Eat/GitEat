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
    private final ApiUtil apiUtil;

    @PostMapping("/regi")
    @Operation(summary = "MM URL 등록", description = "알림에 필요한 MM URL 등록")
    public ResponseEntity<?> saveNotiToken(
            @RequestBody NotiDto notiDto) {
        ResponseEntity<?> response = apiUtil.postApi("/noti/regi", notiDto);

        return ResponseEntity.ok(response.getBody());
    }

    /**
     * 신규 mm 등록
     *
     * @param body
     * @return
     */
    @PostMapping("/addurl")
    public ResponseEntity<?> addUrl(
            @RequestBody NotiDto body) {
        log.info("call addurl Method");
        ResponseEntity<?> response = apiUtil.postApi("/noti/addurl", body);
        return ResponseEntity.ok(response.getBody());
    }

    /**
     * mm url 전달
     *
     * @param repoId
     * @param userId
     * @return
     */
    // URL 조회
    @GetMapping("/geturl/{repoId}/{userId}")
    public ResponseEntity<?> getUrl(
            @PathVariable int repoId,
            @PathVariable int userId) {

        ResponseEntity<?> response = apiUtil.getApi("/noti/geturl" + "/" + repoId + "/" + userId);
        return ResponseEntity.ok(response.getBody());
    }

    /**
     *  mm url 삭제
     * @param repoId
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteurl/{repoId}/{userId}")
    public ResponseEntity<?> deleteUrl(@PathVariable int repoId,
                                       @PathVariable int userId
                                       ) {
        ResponseEntity<?> response = apiUtil.deleteApi("/noti/deleteurl" + "/" + repoId + "/" , null);
        return ResponseEntity.ok(response.getBody());
    }

    /**
     * mm url 업데이트
     *
     * @param body
     * @return
     */
    @PutMapping("/updateurl")
    public ResponseEntity<?> updateUrl(
            @RequestBody NotiDto body) {
        ResponseEntity<?> response = apiUtil.putApi("/noti/updateurl", body);
        return ResponseEntity.ok(response.getBody());
    }
}

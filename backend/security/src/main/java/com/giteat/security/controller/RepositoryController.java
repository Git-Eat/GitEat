package com.giteat.security.controller;

import com.giteat.security.util.ApiUtil;
import com.giteat.security.util.TypeUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repo")
@AllArgsConstructor
@Slf4j
public class RepositoryController {

    private final ApiUtil apiUtil;
    private final TypeUtil typeUtil;

    @GetMapping("")
    @Operation(summary = "Repo 목록 조회", description = "외부 API를 호출하여 Repo 목록을 가져옵니다.")
    public ResponseEntity<?> getRepoList() {
        ResponseEntity<String> response = (ResponseEntity<String>) apiUtil.getApi("/repo");
        Object json = typeUtil.convertJsonToObject(response.getBody());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

//    @PostMapping("/{repoId}")
//    @Operation(summary = "Repo 등록", description = "외부 API를 호출하여 Repo를 등록합니다.")
//    public ResponseEntity<?> insertRepo(@RequestBody Map<String, Integer> repoBody) {
//        log.info("call insertRepo Method");
//        Integer repoId = repoBody.get("repoId");
//        ResponseEntity<String> response = (ResponseEntity<String>) apiUtil.postApi("/repo/" + repoId, repoBody);
//        Object json = typeUtil.convertJsonToObject(response.getBody());
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(json);
//    }

    @DeleteMapping("/{repoId}")
    @Operation(summary = "Repo 삭제", description = "외부 API를 호출하여 Repo를 삭제합니다.")
    public ResponseEntity<?> deleteRepo(@PathVariable int repoId) {
        log.info("call deleteRepo Method");
        ResponseEntity<String> response = (ResponseEntity<String>) apiUtil.deleteApi("/repo/" + repoId , String.valueOf(repoId));
        Object json = typeUtil.convertJsonToObject(response.getBody());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

    @GetMapping("/{repoId}")
    @Operation(summary = "Repo 상세 조회", description = "외부 API를 호출하여 Repo 상세정보를 가져옵니다.")
    public ResponseEntity<?> findRepoById(@PathVariable int repoId) {
        log.info("call findRepoById Method");
        ResponseEntity<String> response = (ResponseEntity<String>) apiUtil.getApi("/repo");
        Object json = typeUtil.convertJsonToObject(response.getBody());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

    @PostMapping("")
    @Operation(summary = "Repo 등록", description = "외부 API를 호출하여 Repo를 등록합니다.")
    public ResponseEntity<?> saveRepositoryData(@RequestBody Map<String, String> repoBody,
                                                @RequestHeader(value = "Authorization") String header) {
        log.info("call insertRepo Method");
        String accessToken = header.split(" ")[1];
        ResponseEntity<String> response = (ResponseEntity<String>) apiUtil.postApi("/repo",repoBody, accessToken);
        System.out.println("@@@@@@@@@ DATA  :" + response.getBody());

        Object json = typeUtil.convertJsonToObject(response.getBody());
        return ResponseEntity.ok(json);
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(json);
    }

}

package com.giteat.security.controller;

import com.giteat.security.util.ApiUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/user")
public class UserController {

    private final ApiUtil api;

    public UserController(ApiUtil api){
        this.api= api;
    }

    /**
     * GET 요청 처리
     * @param param
     * @return
     */
    @GetMapping("/data")
    public ResponseEntity<?> get(@RequestParam String param) {
        // "url"은 실제 API URL로 교체
        return api.getApi("url");
    }

    /**
     * POST 요청 처리
     * @param requestBody
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> post(@RequestBody Object requestBody) {
        // "url"은 실제 API URL로 교체
        return api.postApi("url", requestBody);
    }

    /**
     * PUT 요청 처리
     * @param requestBody
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Object requestBody) {
        // "url"은 실제 API URL로 교체
        return api.putApi("url", requestBody);
    }

    /**
     * DELETE 요청 처리
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String id) {
        // "url"은 실제 API URL로 교체
        return api.deleteApi("url", id);
    }
}

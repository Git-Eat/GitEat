package com.giteat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebBookController {


    /**
     * 테스트용 삭제해야함
     * @param body
     */
    @PostMapping("/event/gitLab")
    public void gitLab(@RequestBody Map<String, Object> body) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("body : " + body);
    }

    @GetMapping("/event/gitLab2")
    public void gitLab() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("GET");
    }

}

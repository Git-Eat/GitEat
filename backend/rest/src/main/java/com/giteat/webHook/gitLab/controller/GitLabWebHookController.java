package com.giteat.webHook.gitLab.controller;


import com.giteat.webHook.gitLab.service.GitLabWebHookService;
import com.giteat.webHook.gitLab.service.GitLabWebHookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/gitlab")
public class GitLabWebHookController {
    private GitLabWebHookService webHookService;

    @Autowired
    public GitLabWebHookController(GitLabWebHookServiceImpl webHookService){
        this.webHookService = webHookService;
    }
    /**
     * @param body
     * @return
     * GitLab WebBook 이 도착했을떄 수신하는 함수
     */
//    @PostMapping("/event")
//    public ResponseEntity<?> webHookEvent(@RequestBody Map<String , Object> body){
//        String eventType = (String) body.get("object_kind");
//        System.out.println("body : " + body);
//        System.out.println("eventType : " + eventType);
//        if(eventType.equals("merge_request")){
//            webHookService.mergeRequestEvent(body);
//        }else if(eventType.equals("note")){
//            webHookService.noteEvent(body);
//        }
//
//        return ResponseEntity.ok().build();
//    }
}

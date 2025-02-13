package com.giteat.common.aop;

import com.giteat.webHook.gitLab.service.GitLabWebHookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class WebHookAspect {
    @Autowired
    private final GitLabWebHookServiceImpl  webHookService;

    public WebHookAspect(GitLabWebHookServiceImpl webHookService) {
        this.webHookService = webHookService;
    }


    //모든 메소드에서 로그인 관련 user는 제외
    @Pointcut("execution(* com.giteat.*.controller.*.*(..)) && !execution(* com.giteat.user.controller.*.*(..))")
    public void allExceptUserControllerMethods() {}

    /**
     * 실행되는 AOP 함수
     */
    @Before("allExceptUserControllerMethods()")
    public void beforeMergeRequest() {
        // 헤더 값 가져오기
        log.info("before AOP trying");
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            // 헤더 값 가져오기
            String headerValue = request.getHeader("Authorization");
            String accessToken = headerValue.split("Bearer ")[1];

            // pr_temp 검사
            webHookService.addMergeRequestData(accessToken);

            // noti_table 검사
//            webHookService.addNoteData(accessToken);
        }catch(Exception e){
            log.info("AOP ERROR");
            e.printStackTrace();
        }

    }

}

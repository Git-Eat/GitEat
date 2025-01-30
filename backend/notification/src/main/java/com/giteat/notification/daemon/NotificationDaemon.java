package com.giteat.notification.daemon;

import com.giteat.notification.api.mm.MatterMostApi;
import com.giteat.notification.daemon.dto.NotificationDto;
import com.giteat.notification.daemon.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@AllArgsConstructor
@EnableScheduling
public class NotificationDaemon {

    private final NotificationService notiService;
    private final MatterMostApi mmApi;

    @Scheduled(fixedRate = 6000)
    public void notiDaemon(){
        //List<NotificationDto> notiList = notiService.selectNotiList();

        mmApi.sendNotification("카카오 보고있나? 조창훈이 간다!! 자 두과자!!");

//        for(NotificationDto noti : notiList){
//
//        }



    }
}

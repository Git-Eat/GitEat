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

    @Scheduled(fixedRate = 180000) // 1초 : 1000  , 세팅값은 3분
    public void notiDaemon(){
        List<NotificationDto> notiList = notiService.selectNotiList();
        List<NotificationDto> notiStatusList = new ArrayList<>();
        for (NotificationDto noti : notiList) {
            String message = noti.getNotiMessage();
            String url = noti.getNotiUrl();
            if(url !=null){ // 경로에 문제가 없을 경우
                boolean notiCheck = mmApi.sendNotification(message , url);
                if(notiCheck){
                    noti.setMsgStatus(1);
                    notiStatusList.add(noti);
                }
            }else{          // 경로 정보가 등록되지 않았을 경우
                noti.setMsgStatus(3);
                notiStatusList.add(noti);
            }

        }
        for(NotificationDto notiStatus : notiStatusList){
            notiService.updateNotiStatus(notiStatus);
        }

    }
}

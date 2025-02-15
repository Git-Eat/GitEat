package com.giteat.notification.daemon;

import com.giteat.notification.api.mm.MatterMostApi;
import com.giteat.notification.constants.NotiConstatns;
import com.giteat.notification.daemon.dto.NotificationDto;
import com.giteat.notification.daemon.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.*;

@Component
@AllArgsConstructor
@EnableScheduling
public class NotificationDaemon {

    private final NotificationService notiService;
    private final MatterMostApi mmApi;
    private final StringBuffer sb = new StringBuffer();

    @Scheduled(fixedRate = 180000) // 1초 : 1000  , 세팅값은 3분
    public void notiDaemon() {
        List<NotificationDto> notiList = notiService.selectNotiList();
        List<NotificationDto> notiStatusList = new ArrayList<>();
        for (NotificationDto noti : notiList) {

            String message = makeMessage(noti);
            String notiToken = noti.getNotiUrl();
            if (notiToken != null) { // 경로에 문제가 없을 경우
                boolean notiCheck = mmApi.sendNotification(message, notiToken);
                if (notiCheck) {
                    noti.setMsgStatus(1);
                    notiStatusList.add(noti);
                }
            } else {          // 경로 정보가 등록되지 않았을 경우
                noti.setMsgStatus(3);
                notiStatusList.add(noti);
            }

        }
        for (NotificationDto notiStatus : notiStatusList) {
            notiService.updateNotiStatus(notiStatus);
        }

    }

    public String makeMessage(NotificationDto notiDto) {
        StringBuilder sb = new StringBuilder();

        int type = notiDto.getNotiType();

        if (type == 1) { // PR
            sb.append(NotiConstatns.PR_MESSAGE_TOP).append("\n");
            sb.append(NotiConstatns.MESSAGE_TITLE).append(notiDto.getNotiMessage()).append("\n");
        } else if (type == 2) { // Comment
            sb.append(NotiConstatns.COMMENT_MESSAGE_TOP).append("\n");
        } else if (type == 3) { // Reply
            sb.append(NotiConstatns.REPLY_MESSAGE_TOP).append("\n");
        }

        sb.append(NotiConstatns.MESSAGE_WRITTER).append(notiDto.getUserName()).append("\n");
        sb.append(NotiConstatns.MESSAGE_URL).append("(").append(notiDto.getNotiUrl()).append(")");

        return sb.toString();
    }

}

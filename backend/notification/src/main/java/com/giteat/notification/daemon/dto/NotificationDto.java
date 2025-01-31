package com.giteat.notification.daemon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private int notiId;
    private int userId;
    private int repoId;
    private String notiMessage;
    private int sendType;
    private int notiType;
    private String sendTime;
    private String notiUrl;
    private int msgStatus;
}

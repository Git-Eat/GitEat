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
    private String userName; // 🔹 추가됨
    private int repoId;
    private String notiMessage;
    private int sendType;
    private int notiType;
    private String sendTime;
    private String notiUrl; // 🔹 추가됨
    private String notiToken; // 🔹 추가됨
    private int msgStatus;
}

package com.giteat.noti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class NotiDto {
    private int repoId;
    private int userId;
    private String notiToken;

}
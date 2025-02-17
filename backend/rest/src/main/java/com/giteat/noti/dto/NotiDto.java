package com.giteat.noti.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotiDto {
    private int repoId;
    private int userId;
    private String notiToken;

}
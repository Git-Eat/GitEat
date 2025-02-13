package com.giteat.security.dto;

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

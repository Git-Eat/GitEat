package com.giteat.statistics.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsDto {
    private int userId;
    private String name;
    private String userName;
    private String avatarUrl;
}

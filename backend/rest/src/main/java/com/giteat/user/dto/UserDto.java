package com.giteat.user.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userId;
    private String email;
    private String name;
    private String avatarUrl;
    private String mmWebHook;


}

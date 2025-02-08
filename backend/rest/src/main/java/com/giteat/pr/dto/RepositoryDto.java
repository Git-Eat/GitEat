package com.giteat.pr.dto;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryDto {
    private int repoId; // repository 고유 ID
    private String name; // repository 이름
    private String description; // repo 설명
    private String githubUrl; // 깃허브 URL
    private String gitlabUrl; // 깃랩 URL
    private String createAt; // repo 생성날짜
    private String ownerName; // repository 소유자
    private int access; // 접근권한

}

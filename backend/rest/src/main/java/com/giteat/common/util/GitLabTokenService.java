package com.giteat.common.util;

import com.giteat.common.gitLab.mapper.GitLabTokenMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GitLabTokenService {

    private final GitLabTokenMapper gitLabTokenMapper;

    /**
     * jwt 토큰을 사용해서 gitLab accessToken을 가져오는 코드
     * @param jwtAccessToken
     * @return
     */
    public String getAccessToken(String jwtAccessToken){
        return gitLabTokenMapper.getAccessToken(jwtAccessToken);
    }
    public String getAccessTokenById(String userId){
        return gitLabTokenMapper.getAccessTokenById(userId);
    }
}

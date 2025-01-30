package com.giteat.common.util;

import com.giteat.common.gitLab.mapper.GitLabTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitLabTokenService {

    @Autowired
    private GitLabTokenService gitLabTokenService;

    /**
     * jwt 토큰을 사용해서 gitLab accessToken을 가져오는 코드
     * @param jwtAccessToken
     * @return
     */
    public String getAccessToken(String jwtAccessToken){
        return gitLabTokenService.getAccessToken(jwtAccessToken);
    }
}

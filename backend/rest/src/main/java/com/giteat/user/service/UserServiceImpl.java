package com.giteat.user.service;

import com.giteat.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    /**
     * repository id를 통해 해당 repository 사용자 토큰 가져오는 함수
     * @param repository
     * @return
     */
    public List<UserDto> getUserTokens(String repository) {
        return getUserTokens(repository);
    }
}

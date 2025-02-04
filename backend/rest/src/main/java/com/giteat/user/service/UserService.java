package com.giteat.user.service;

import com.giteat.user.dto.UserDto;
import java.util.List;

public interface UserService {

    /**
     * repository id를 통해 해당 repository 사용자 토큰 가져오는 코드
     * @param repository
     * @return
     */
    List<UserDto> getUserTokens(String repository);
}

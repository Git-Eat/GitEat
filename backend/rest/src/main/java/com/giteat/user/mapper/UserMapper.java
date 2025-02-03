package com.giteat.user.mapper;

import com.giteat.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * repository id를 통해 해당 repository 사용자 토큰 가져오는 함수
     * @param directory
     * @return
     */
    List<UserDto> getUserTokens(String directory);
}

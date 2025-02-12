package com.giteat.security.user.mapper;

import com.giteat.security.user.dto.OAuthTokenDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Lazy;

@Mapper
public interface OauthMapper {

    int updateNewToken(OAuthTokenDto oauthTokenDto);
}

package com.giteat.user.service;

import com.giteat.user.dto.OAuthTokenDto;


public interface OAuthService {

    void saveToken(OAuthTokenDto oAuthTokenDto);

    OAuthTokenDto refreshToken(OAuthTokenDto tokenRequest);

//    void logout(OAuthTokenDto oAuthTokenDto);

}

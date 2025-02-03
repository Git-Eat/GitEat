package com.giteat.user.model.service;

import com.giteat.user.model.dto.OAuthTokenDto;


public interface OAuthService {

    void saveToken(OAuthTokenDto oAuthTokenDto);

    OAuthTokenDto refreshToken(OAuthTokenDto tokenRequest);
}

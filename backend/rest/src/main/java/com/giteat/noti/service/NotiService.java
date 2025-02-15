package com.giteat.noti.service;

import com.giteat.noti.dto.NotiDto;

public interface NotiService {
    int saveNotiToken(NotiDto notiDto);

    int addUrl(NotiDto notiDto);

    NotiDto getUrl(NotiDto notiDto);

    int deleteUrl(NotiDto notiDto);

    int updateUrl(NotiDto notiDto);

}

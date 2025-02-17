package com.giteat.noti.mapper;

import com.giteat.noti.dto.NotiDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotiMapper {
    int saveNotiToken(NotiDto notiDto);

    int addUrl(NotiDto notiDto);

    NotiDto getUrl(NotiDto notiDto);

    int deleteUrl(NotiDto notiDto);

    int updateUrl(NotiDto notiDto);
}

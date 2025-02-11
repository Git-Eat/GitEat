package com.giteat.noti.mapper;

import com.giteat.noti.dto.NotiDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotiMapper {
    int saveNotiToken(NotiDto notiDto);
}

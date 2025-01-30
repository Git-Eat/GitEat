package com.giteat.notification.daemon.mapper;

import com.giteat.notification.daemon.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper
public interface NotificationMapper {
    List<NotificationDto> selectNotiList();

}

package com.giteat.notification.daemon.service;

import com.giteat.notification.daemon.dto.NotificationDto;
import com.giteat.notification.daemon.mapper.NotificationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationMapper notiMapper;

    public List<NotificationDto> selectNotiList(){
        return notiMapper.selectNotiList();
    }

    public void updateNotiStatus(NotificationDto notiStatusDto){
        notiMapper.updateNotiStatus(notiStatusDto);
    };

}

package com.giteat.noti.service;

import com.giteat.noti.dto.NotiDto;
import com.giteat.noti.mapper.NotiMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotiServiceImpl implements NotiService {

    private NotiMapper notiMapper;

    @Override
    public int saveNotiToken(NotiDto notiDto) {
        return notiMapper.saveNotiToken(notiDto);
    }
}

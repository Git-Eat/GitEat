package com.giteat.noti.service;

import com.giteat.noti.dto.NotiDto;
import com.giteat.noti.mapper.NotiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements NotiService {

    private final NotiMapper notiMapper;

    @Override
    public int saveNotiToken(NotiDto notiDto) {
        return notiMapper.saveNotiToken(notiDto);
    }

    /**
     * 신규 mm url 등록
     */
    @Override
    public int addUrl(NotiDto notiDto) {
        return notiMapper.addUrl(notiDto);
    }

    /**
     * 등록된 mm url 가져오기
     */
    @Override
    public NotiDto getUrl(NotiDto notiDto) {
        return notiMapper.getUrl(notiDto);
    }

    /**
     * 등록된 mm url 삭제
     */
    @Override
    public int deleteUrl(NotiDto notiDto) {
        return notiMapper.deleteUrl(notiDto);
    }

    /**
     * 등록된 mm url 수정
     */
    @Override
    public int updateUrl(NotiDto notiDto) {
        return notiMapper.updateUrl(notiDto);
    }
}

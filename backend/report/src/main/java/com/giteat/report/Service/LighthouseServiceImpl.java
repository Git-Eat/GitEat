package com.giteat.report.Service;

import com.giteat.report.Entity.LighthouseEntity;
import com.giteat.report.Repository.LighthouseRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LighthouseServiceImpl implements LighthouseService {

    private final LighthouseRepo lighthouseRepository;

    public LighthouseServiceImpl(LighthouseRepo lighthouseRepository) {
        this.lighthouseRepository = lighthouseRepository;
    }

    @Override
    @Transactional
    public void saveLighthouseResult(LighthouseEntity lighthouseResult) {
        try {
            lighthouseRepository.save(lighthouseResult);
            System.out.println("✅ Lighthouse result saved successfully!");
        } catch (Exception e) {
            System.err.println("❌ Failed to save Lighthouse result: " + e.getMessage());
            throw e; // 예외를 다시 던져 Controller로 전달
        }
    }
}

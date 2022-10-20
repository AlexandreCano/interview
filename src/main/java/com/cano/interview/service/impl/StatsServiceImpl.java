package com.cano.interview.service.impl;

import com.cano.interview.entity.Payload;
import com.cano.interview.entity.Stats;
import com.cano.interview.repository.StatsRepository;
import com.cano.interview.service.StatsService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    public StatsServiceImpl(final StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public Stats save(final Payload payload) {
        Stats currentStats = statsRepository.findByCustomerContentAndTime(payload.getCustomer(), payload.getContent(), LocalDateTime.now().plusMinutes(INTERVAL));

        if (currentStats != null) {
            currentStats.setCdn(payload.getCdn() + currentStats.getCdn());
            currentStats.setP2p(payload.getP2p() + currentStats.getP2p());
            log.info("Update stats :");
        } else {
            currentStats = createStatsFromPayload(payload);
            log.info("Create stats :");
        }
        log.info(currentStats.toString());

        statsRepository.save(currentStats);

        return currentStats;
    }

    private Stats createStatsFromPayload(final Payload payload) {
        Stats stats = new Stats();
        stats.setCustomer(payload.getCustomer());
        stats.setContent(payload.getContent());
        stats.setCdn(payload.getCdn());
        stats.setP2p(payload.getP2p());
        stats.setTime(LocalDateTime.now().plusMinutes(INTERVAL));
        return stats;
    }
}

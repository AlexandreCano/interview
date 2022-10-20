package com.cano.interview.service.impl;

import com.cano.interview.entity.Payload;
import com.cano.interview.entity.Stats;
import com.cano.interview.repository.StatsRepository;
import com.cano.interview.service.StatsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Service which manage the stats.
 */
@Service
@Log
public class StatsServiceImpl implements StatsService {
    /** The stats time window. */
    @Value("${interview.interval}")
    private int interval;

    /** The stats database repository. */
    private final StatsRepository statsRepository;
    /** The cache of token, used to know if it's a new session. */
    private final List<String> tokenCache = new ArrayList<>();
    /** The timer to remove the token every interval. */
    private final Timer timer = new Timer();

    /**
     * The service constructor.
     *
     * @param statsRepository The stats database repository.
     */
    public StatsServiceImpl(final StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stats save(final Payload payload) {
        Stats currentStats = statsRepository.findByCustomerContentAndTime(payload.getCustomer(), payload.getContent(), LocalDateTime.now().plusSeconds(interval));

        if (currentStats != null) {
            currentStats.setCdn(payload.getCdn() + currentStats.getCdn());
            currentStats.setP2p(payload.getP2p() + currentStats.getP2p());
            if (!tokenCache.contains(payload.getToken())) {
                currentStats.setSessions(currentStats.getSessions() + 1);
                addTokenToCache(payload.getToken(), interval);
            }
            log.info("Update stats :");
        } else {
            currentStats = createStatsFromPayload(payload);
            log.info("Create stats :");
        }
        log.info(currentStats.toString());

        statsRepository.save(currentStats);

        return currentStats;
    }

    /**
     * Create a new stats object from a payload.
     *
     * @param payload The payload to extract stats.
     * @return the extracted stats.
     */
    private Stats createStatsFromPayload(final Payload payload) {
        Stats stats = new Stats();
        stats.setCustomer(payload.getCustomer());
        stats.setContent(payload.getContent());
        stats.setCdn(payload.getCdn());
        stats.setP2p(payload.getP2p());
        stats.setTime(LocalDateTime.now().plusSeconds(interval));
        stats.setSessions(1);
        addTokenToCache(payload.getToken(), interval);
        return stats;
    }

    /**
     * Add a new token in the cache of token.
     *
     * @param token The token to save.
     * @param timeToLive The time in seconds before it removes the token from the cache.
     */
    public void addTokenToCache(final String token, final int timeToLive) {
        tokenCache.add(token);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removeTokenFromCache(token);
            }
        }, (long) timeToLive * 1000);
    }

    /**
     * Remove the token from the cache.
     *
     * @param token The token to remove.
     */
    private void removeTokenFromCache(final String token) {
        tokenCache.remove(token);
    }
}

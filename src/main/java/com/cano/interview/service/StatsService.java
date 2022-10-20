package com.cano.interview.service;

import com.cano.interview.entity.Payload;
import com.cano.interview.entity.Stats;

/**
 * The interface for the service which manage the stats.
 */
public interface StatsService {
    /**
     * Process a payload to extract the stats in database.
     *
     * @param payload the payload.
     * @return the saved stats.
     */
    Stats save(Payload payload);
}

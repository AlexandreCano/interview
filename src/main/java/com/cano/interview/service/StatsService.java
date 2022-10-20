package com.cano.interview.service;

import com.cano.interview.entity.Payload;
import com.cano.interview.entity.Stats;

public interface StatsService {
    public final static Long INTERVAL = 5L;

    Stats save(Payload payload);
}

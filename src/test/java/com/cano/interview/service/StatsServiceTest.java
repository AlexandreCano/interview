package com.cano.interview.service;

import com.cano.interview.entity.Payload;
import com.cano.interview.entity.Stats;
import com.cano.interview.repository.StatsRepository;
import com.cano.interview.service.impl.StatsServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatsServiceTest {

    private StatsRepository statsRepository = mock(StatsRepository.class);
    private StatsService statsService = new StatsServiceImpl(statsRepository);

    @Test
    public void testSave() {
        Payload payload = new Payload();
        payload.setToken("token");
        payload.setCustomer("customer");
        payload.setContent("content");
        payload.setTimespan(30000L);
        payload.setP2p(560065L);
        payload.setCdn(321123L);
        payload.setSessionDuration(120000);

        Stats result = statsService.save(payload);

        assertEquals(payload.getContent(), result.getContent());
        assertEquals(payload.getCustomer(), result.getCustomer());
        assertEquals(payload.getP2p(), result.getP2p());
        assertEquals(payload.getCdn(), result.getCdn());
    }

    @Test
    public void testSaveUpdate() {
        Payload payload = new Payload();
        payload.setToken("token");
        payload.setCustomer("customer");
        payload.setContent("content");
        payload.setTimespan(30000L);
        payload.setP2p(560065L);
        payload.setCdn(321123L);
        payload.setSessionDuration(120000);

        Stats stats = new Stats();
        stats.setTime(LocalDateTime.now().plusMinutes(4));
        stats.setCustomer("customer");
        stats.setContent("content");
        stats.setP2p(500000L);
        stats.setCdn(300000L);

        when(statsRepository.findByCustomerContentAndTime(eq(payload.getCustomer()), eq(payload.getContent()), any()))
                .thenReturn(stats);

        Stats result = statsService.save(payload);

        assertEquals(stats.getTime(), result.getTime());
        assertEquals(payload.getContent(), result.getContent());
        assertEquals(payload.getCustomer(), result.getCustomer());
        assertEquals(500000L + payload.getP2p(), result.getP2p());
        assertEquals(300000L + payload.getCdn(), result.getCdn());
    }
}

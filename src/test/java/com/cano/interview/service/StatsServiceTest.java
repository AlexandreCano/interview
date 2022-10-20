package com.cano.interview.service;

import com.cano.interview.entity.Payload;
import com.cano.interview.entity.Stats;
import com.cano.interview.repository.StatsRepository;
import com.cano.interview.service.impl.StatsServiceImpl;
import org.junit.Test;

import java.time.LocalDateTime;

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
        //GIVEN a payload of a new session.
        Payload payload = new Payload();
        payload.setToken("token");
        payload.setCustomer("customer");
        payload.setContent("content");
        payload.setTimespan(30000L);
        payload.setP2p(560065L);
        payload.setCdn(321123L);
        payload.setSessionDuration(120000);

        //WHEN we save the payload.
        Stats result = statsService.save(payload);

        //THEN the stat is saved.
        assertEquals(payload.getContent(), result.getContent());
        assertEquals(payload.getCustomer(), result.getCustomer());
        assertEquals(payload.getP2p(), result.getP2p());
        assertEquals(payload.getCdn(), result.getCdn());
        assertEquals(1, result.getSessions());
    }

    @Test
    public void testUpdate() {
        //GIVEN a payload of an already existing session.
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
        stats.setSessions(5);

        when(statsRepository.findByCustomerContentAndTime(eq(payload.getCustomer()), eq(payload.getContent()), any()))
                .thenReturn(stats);

        //WHEN we save the payload.
        Stats result = statsService.save(payload);

        //THEN the stat is updated.
        assertEquals(stats.getTime(), result.getTime());
        assertEquals(payload.getContent(), result.getContent());
        assertEquals(payload.getCustomer(), result.getCustomer());
        assertEquals(500000L + payload.getP2p(), result.getP2p());
        assertEquals(300000L + payload.getCdn(), result.getCdn());
        assertEquals(6, result.getSessions());
    }

    @Test
    public void testCache() throws InterruptedException {
        //GIVEN a payload of an already existing session and a token already in cache.
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
        stats.setSessions(5);

        when(statsRepository.findByCustomerContentAndTime(eq(payload.getCustomer()), eq(payload.getContent()), any()))
                .thenReturn(stats);
        ((StatsServiceImpl) statsService).addTokenToCache("token", 1);

        //WHEN we save the payload.
        Stats result = statsService.save(payload);

        //THEN session is not updated.
        assertEquals(5, result.getSessions());

        //WHEN we wait 2s.
        Thread.sleep(2000);
        result = statsService.save(payload);

        //THEN the token is removed from cache and the session is updated
        assertEquals(6, result.getSessions());
    }
}

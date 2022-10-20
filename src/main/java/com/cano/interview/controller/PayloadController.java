package com.cano.interview.controller;

import com.cano.interview.entity.Payload;
import com.cano.interview.entity.Stats;
import com.cano.interview.service.StatsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The REST controller for stats endpoint.
 */
@RestController
public class PayloadController {
    /**
     * The service which manage the stats.
     */
    private final StatsService statsService;

    /**
     * The controller constructor.
     * @param statsService The service which manage the stats.
     */
    public PayloadController(final StatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * The POST endpoint to manage payloads.
     *
     * @param payload The payload to extract stats.
     * @return The stats saved in database.
     */
    @PostMapping(value = {"/stats"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Stats save(@RequestBody Payload payload) {
        return statsService.save(payload);
    }
}

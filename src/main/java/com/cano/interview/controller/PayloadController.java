package com.cano.interview.controller;

import com.cano.interview.entity.Payload;
import com.cano.interview.entity.Stats;
import com.cano.interview.service.StatsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayloadController {

    private final StatsService statsService;

    public PayloadController(final StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping(value = {"/stats"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Stats save(@RequestBody Payload payload) {
        return statsService.save(payload);
    }
}

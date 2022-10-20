package com.cano.interview.entity;

import lombok.Data;

@Data
public class Payload {

    private String token;

    private String customer;

    private String content;

    private long timespan;

    private long p2p;

    private long cdn;

    private long sessionDuration;
}

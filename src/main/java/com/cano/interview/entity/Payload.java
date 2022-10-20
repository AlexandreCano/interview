package com.cano.interview.entity;

import lombok.Data;

/**
 * The request payload.
 */
@Data
public class Payload {
    /**
     * Unique id generated at the beginning of each video session (i.e when a new video is started),
     * and sent in all the payloads of that video session. Each token is then unique to a video session.
     */
    private String token;

    /**
     * The name of the company broadcasting the video being played.
     */
    private String customer;

    /**
     * The name of the content being played.
     */
    private String content;

    /**
     * the time (in milliseconds) elapsed since the last time the device sent a payload in that video session.
     */
    private long timespan;

    /**
     * The volume (in bytes) downloaded from other devices in Peer-to-Peer since the last time the device sent a payload in that video session.
     */
    private long p2p;

    /**
     * The volume (in bytes) downloaded from the broadcaster's servers (cdn is short for Content Delivery Network).
     */
    private long cdn;

    /**
     * Total time elapsed (in milliseconds) since the beginning of the video session.
     */
    private long sessionDuration;
}

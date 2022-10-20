package com.cano.interview.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The stats extracted from payloads.
 */
@Data
@Entity
@Table(name = "stats", schema = "test_schema")
public class Stats implements Serializable {
    /**
     * The stats auto generated id.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The time when the stats time window ends.
     */
    @Column(name = "time")
    private LocalDateTime time;

    /**
     * The stats customer.
     */
    @Column(name = "customer")
    private String customer;

    /**
     * The stats content.
     */
    @Column(name = "content")
    private String content;

    /**
     * The sum (in bytes) of all the data downloaded via P2p during the time window.
     */
    @Column(name = "p2p")
    private long p2p;

    /**
     * The sum (in bytes) of all the data downloaded via the CDN during the time window.
     */
    @Column(name = "cdn")
    private long cdn;

    /**
     * Number of video sessions that started during the time window.
     */
    @Column(name = "sessions")
    private int sessions;
}

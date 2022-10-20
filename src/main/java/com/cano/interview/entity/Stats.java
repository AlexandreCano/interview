package com.cano.interview.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stats", schema = "test_schema")
public class Stats implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "customer")
    private String customer;

    @Column(name = "content")
    private String content;

    @Column(name = "p2p")
    private long p2p;

    @Column(name = "cdn")
    private long cdn;
}

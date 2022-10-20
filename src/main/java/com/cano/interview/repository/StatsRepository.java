package com.cano.interview.repository;

import com.cano.interview.entity.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface StatsRepository extends JpaRepository<Stats, Integer> {
    /**
     * Request a stats object from the database.
     *
     * @param customer The stats customer.
     * @param content The stats content.
     * @param time The time window.
     * @return The stats or null if not found.
     */
    @Query("SELECT s FROM Stats s WHERE s.customer = :customer AND s.content = :content AND " +
            "s.time > CURRENT_TIMESTAMP AND s.time < :time")
    Stats findByCustomerContentAndTime(@Param("customer") String customer, @Param("content") String content, @Param("time") LocalDateTime time);
}

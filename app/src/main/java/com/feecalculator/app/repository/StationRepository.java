package com.feecalculator.app.repository;

import com.feecalculator.app.entity.Station;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StationRepository extends CrudRepository<Station, Long> {
    @Query(value = "SELECT * FROM station s " +
            "WHERE s.name = :name AND s.timestamp <= :timestamp " +
            "ORDER BY s.timestamp DESC LIMIT 1", nativeQuery = true )
    Station findFirstByNameBeforeTimestamp(@Param("name") String name,
                                           @Param("timestamp") Long timestamp);
}

package com.mini_decoder.mini_decoder.repository;

import com.mini_decoder.mini_decoder.entity.SensorReadings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<SensorReadings,Long> {
    @Query("SELECT s.value FROM SensorReadings s WHERE s.buildingId = :buildingId AND s.sensorId = :sensorId ORDER BY s.timestamp DESC LIMIT :window")
    List<Double> findRecentValues(@Param("buildingId") String buildingId, @Param("sensorId") String sensorId, @Param("window") int window);

    Optional<SensorReadings> findByBuildingIdAndSensorId(String buildingId, String sensorId);
}

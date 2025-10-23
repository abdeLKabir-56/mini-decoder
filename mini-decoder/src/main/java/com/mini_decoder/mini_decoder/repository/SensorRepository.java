package com.mini_decoder.mini_decoder.repository;

import com.mini_decoder.mini_decoder.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor,Long> {
}

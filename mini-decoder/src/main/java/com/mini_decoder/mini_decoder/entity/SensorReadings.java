package com.mini_decoder.mini_decoder.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "sensor_readings")
public class SensorReadings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String buildingId;
    private String sensorId;
    private LocalDateTime timestamp;
    private Double value;

}

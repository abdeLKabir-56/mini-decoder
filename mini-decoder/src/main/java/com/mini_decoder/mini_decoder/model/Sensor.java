package com.mini_decoder.mini_decoder.model;

import org.springframework.data.annotation.Id;

@Entity
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String buildingId;
    private String sensorId;
    private LocalDateTime timestamp;
    private String value;

}

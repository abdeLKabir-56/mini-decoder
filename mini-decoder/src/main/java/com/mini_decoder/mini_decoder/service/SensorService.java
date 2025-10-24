package com.mini_decoder.mini_decoder.service;

import java.time.LocalDateTime;

public interface SensorService {
    String saveData(String buildingId, String sensorId, LocalDateTime timestamp, String value);
    String forcasting();
}

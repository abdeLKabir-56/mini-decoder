package com.mini_decoder.mini_decoder.service;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorService {
    String saveData(String buildingId, String sensorId, LocalDateTime timestamp, Double value);
    List<Double> predict(String buildingId, String sensorId, int k, int window);
}

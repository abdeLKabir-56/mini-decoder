package com.mini_decoder.mini_decoder.service;

import com.mini_decoder.mini_decoder.model.Sensor;
import com.mini_decoder.mini_decoder.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    @Override
    public String saveData(String buildingId, String sensorId, LocalDateTime timestamp, String value) {
        Sensor sensor = new Sensor();
        sensor.setBuildingId(buildingId);
        sensor.setSensorId(sensorId);
        sensor.setTimestamp(timestamp);
        sensor.setValue(value)
        sensorRepository.save(sensor);
        return "Success";
    }

}

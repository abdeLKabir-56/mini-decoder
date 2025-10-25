package com.mini_decoder.mini_decoder.service;


import com.mini_decoder.mini_decoder.entity.SensorReadings;
import com.mini_decoder.mini_decoder.exceptions.SensorDataSaveException;
import com.mini_decoder.mini_decoder.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    @Override
    public String saveData(String buildingId, String sensorId, LocalDateTime timestamp, Double value) {
        try {
            SensorReadings sensor = new SensorReadings();
            sensor.setBuildingId(buildingId);
            sensor.setSensorId(sensorId);
            sensor.setTimestamp(timestamp);
            sensor.setValue(value);

            sensorRepository.save(sensor);
            return "Sensor data saved successfully for sensor ID: " + sensorId + " in building: " + buildingId;
        } catch (Exception e) {
            throw new SensorDataSaveException("Error saving sensor data for sensor ID: " + sensorId, e);
        }
    }


    /**
     * Predict k future values for (buildingId, sensorId).
     * Uses moving average of up to 'window' latest points. If not enough, uses whatever is available.
     */
    public List<Double> predict(String buildingId, String sensorId, int k, int window) {
        List<Double> recent = sensorRepository.findRecentValues(buildingId, sensorId, window);
        double avg;
        if (recent == null || recent.isEmpty()) {
            // no data -> return zeros (or NaN). We choose zeros for simplicity.
            avg = 0.0;
        } else {
            double sum = 0.0;
            for (Double v : recent) sum += v;
            avg = sum / recent.size();
        }
        List<Double> preds = new ArrayList<>();
        for (int i = 0; i < k; i++) preds.add(avg);
        return preds;
    }

}

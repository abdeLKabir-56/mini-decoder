package com.mini_decoder.mini_decoder.controller;

import com.mini_decoder.mini_decoder.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping
@RequiredArgsConstructor
@Tag(
        name = "Sensor Controller",
        description = "Handles operations related to sensor data — saving, retrieving, and managing sensor readings for buildings."
)
public class SensorController {

    private final SensorService sensorService;

    @Operation(
            summary = "Ingest endpoint to store sensor readings",
            description = "Stores sensor readings (buildingId, sensorId, timestamp, value) in PostgreSQL."
    )
    @PostMapping("/ingest")
    public ResponseEntity<String> saveSensor(
            @RequestParam String buildingId,
            @RequestParam String sensorId,
            @RequestParam LocalDateTime timestamp,
            @RequestParam Double value
    ) {
        String response = sensorService.saveData(buildingId, sensorId, timestamp, value);
        return ResponseEntity.ok(response);
    }
    @Operation(
            summary = "endpoint returning 3 predicted future values using a simple model",
            description = "endpoint returning 3 predicted future values using a simple model"
    )    @GetMapping ("/forecast")
    public ResponseEntity<List<Double>> forecast(
            @RequestParam String buildingId,
            @RequestParam String sensorId,
            @RequestParam(defaultValue = "3") int horizon,
            @RequestParam(defaultValue = "5") int window
    ) {
        List<Double> preds = sensorService.predict(buildingId, sensorId, horizon, window);
        return ResponseEntity.ok(preds);
    }
}

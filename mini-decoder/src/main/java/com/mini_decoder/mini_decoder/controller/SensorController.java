package com.mini_decoder.mini_decoder.controller;

import com.mini_decoder.mini_decoder.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@CrossOrigin("*")
@RequestMapping
@RequiredArgsConstructor
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
            @RequestParam String value
    ) {
        String response = sensorService.saveData(buildingId, sensorId, timestamp, value);
        return ResponseEntity.ok(response);
    }
}

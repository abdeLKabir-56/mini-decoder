package com.mini_decoder.mini_decoder.controller;

import com.mini_decoder.mini_decoder.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping
@RequiredArgsConstructor
public class ForcastController {
    private final SensorService sensorService;

    @Operation(
            summary = "endpoint returning 3 predicted future values using a simple model",
            description = "endpoint returning 3 predicted future values using a simple model"
    )
    @GetMapping ("/forecast")
    public ResponseEntity<String> forcasting(
    ) {
        String response = sensorService.forcasting();
        return ResponseEntity.ok(response);
    }
}

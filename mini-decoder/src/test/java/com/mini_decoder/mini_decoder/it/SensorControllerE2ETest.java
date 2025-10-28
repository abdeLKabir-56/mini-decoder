package com.mini_decoder.mini_decoder.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini_decoder.mini_decoder.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * End-to-end test for SensorController.
 * It loads the full Spring context, uses MockMvc to call the endpoints,
 * and verifies database persistence + forecast behavior.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SensorControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        sensorRepository.deleteAll(); // ensure clean state before each test
    }

    @Test
    void testIngestAndForecast() throws Exception {
        // given: 5 sensor readings saved through /ingest endpoint
        String buildingId = "B1";
        String sensorId = "S1";

        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post("/ingest")
                            .param("buildingId", buildingId)
                            .param("sensorId", sensorId)
                            .param("timestamp", LocalDateTime.now().minusMinutes(i).toString())
                            .param("value", String.valueOf(20 + i)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Sensor data saved successfully for sensor ID: "
                            + sensorId + " in building: " + buildingId));
        }

        // verify database
        assertThat(sensorRepository.findAll()).hasSize(5);

        // when: forecast is requested
        var result = mockMvc.perform(get("/forecast")
                        .param("buildingId", buildingId)
                        .param("sensorId", sensorId)
                        .param("horizon", "3")
                        .param("window", "5"))
                .andExpect(status().isOk())
                .andReturn();

        // then: the forecast should contain 3 predicted values
        String json = result.getResponse().getContentAsString();
        Double[] preds = objectMapper.readValue(json, Double[].class);
        assertThat(preds).hasSize(3);

        // The predicted values should be close to the average of 20..24 = 22.0
        assertThat(preds[0]).isEqualTo(22.0);
    }
}

package com.mini_decoder.mini_decoder.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.mini_decoder.mini_decoder.service.SensorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SensorController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SensorControllerTest {
    @Autowired
    private SensorController sensorController;

    @MockitoBean
    private SensorService sensorService;

    /**
     * Test {@link SensorController#forecast(String, String, int, int)}.
     *
     * <p>Method under test: {@link SensorController#forecast(String, String, int, int)}
     */
    @Test
    @DisplayName("Test forecast(String, String, int, int)")
    void testForecast() throws Exception {
        // Arrange
        when(sensorService.predict(Mockito.<String>any(), Mockito.<String>any(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder paramResult =
                MockMvcRequestBuilders.get("/forecast").param("buildingId", "foo");
        MockHttpServletRequestBuilder paramResult2 =
                paramResult.param("horizon", String.valueOf(1)).param("sensorId", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("window", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sensorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link SensorController#saveSensor(String, String, LocalDateTime, Double)}.
     *
     * <p>Method under test: {@link SensorController#saveSensor(String, String, LocalDateTime,
     * Double)}
     */
    @Test
    @DisplayName("Test saveSensor(String, String, LocalDateTime, Double)")
    void testSaveSensor() throws Exception {
        // Arrange
        when(sensorService.saveData(
                Mockito.<String>any(),
                Mockito.<String>any(),
                Mockito.<LocalDateTime>any(),
                Mockito.<Double>any()))
                .thenReturn("Save Data");
        MockHttpServletRequestBuilder paramResult =
                MockMvcRequestBuilders.post("/ingest").param("buildingId", "foo").param("sensorId", "foo");
        MockHttpServletRequestBuilder paramResult2 =
                paramResult.param("timestamp", String.valueOf(LocalDate.of(1970, 1, 1).atStartOfDay()));
        MockHttpServletRequestBuilder requestBuilder =
                paramResult2.param("value", String.valueOf(10.0d));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sensorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Save Data"));
    }
}

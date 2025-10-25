package com.mini_decoder.mini_decoder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mini_decoder.mini_decoder.repository.SensorRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SensorServiceImpl.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SensorServiceImplTest {
    @MockitoBean
    private SensorRepository sensorRepository;

    @Autowired
    private SensorServiceImpl sensorServiceImpl;

    /**
     * Test {@link SensorServiceImpl#predict(String, String, int, int)}.
     *
     * <ul>
     *   <li>Then return first doubleValue is zero.
     * </ul>
     *
     * <p>Method under test: {@link SensorServiceImpl#predict(String, String, int, int)}
     */
    @Test
    @DisplayName("Test predict(String, String, int, int); then return first doubleValue is zero")
    void testPredict_thenReturnFirstDoubleValueIsZero() {
        // Arrange
        when(sensorRepository.findRecentValues(Mockito.<String>any(), Mockito.<String>any(), anyInt()))
                .thenReturn(new ArrayList<>());

        // Act
        List<Double> actualPredictResult = sensorServiceImpl.predict("42", "42", 1, 1);

        // Assert
        verify(sensorRepository).findRecentValues("42", "42", 1);
        assertEquals(1, actualPredictResult.size());
        assertEquals(0.0d, actualPredictResult.get(0).doubleValue());
    }
}

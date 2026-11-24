package com.example.demo.figure;


import com.example.demo.DemoApplication;
import com.example.demo.figure.repository.FigureRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FigureControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FigureRepository figureRepository;

    @Autowired
    private MockMvc postman;

    static Stream<Arguments> incorrectParameters() {
        return Stream.of(
                Arguments.of("circle", Map.of(), 400),
                Arguments.of("circle", Map.of("side", 2.0), 400),
                Arguments.of("circle", params("radius", null), 400),
                Arguments.of("circle", Map.of("radius", 0.0), 400),
                Arguments.of("circle", Map.of("radius", -5.0), 400),

                Arguments.of("square", Map.of(), 400),
                Arguments.of("square", Map.of("radius", 2.0), 400),
                Arguments.of("square", params("side", null), 400),
                Arguments.of("square", Map.of("side", -1.0), 400),

                Arguments.of("isoscelesTriangle", Map.of("side", 5.0), 400),
                Arguments.of("isoscelesTriangle", params("side", null, "base", 6.0), 400),
                Arguments.of("isoscelesTriangle", Map.of("side", -5.0, "base", 6.0), 400),
                Arguments.of("isoscelesTriangle", Map.of("side", 1.0, "base", 10.0), 400),

                Arguments.of("hexagon", Map.of("side", 1.0), 400)
        );
    }

    @ParameterizedTest(name = "[{index}] {0} {1} -> {2}")
    @MethodSource("incorrectParameters")
    void itDoesNotCreatesFiguresWhenIncorrectParameters(String type, Map<String, Object> params, int expectedStatus) throws Exception {
        long countBefore = figureRepository.count();

        Map<String, Object> body = Map.of("type", type, "params", params);

        postman.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is(expectedStatus));

        assertThat(figureRepository.count()).isEqualTo(countBefore);
    }

    private static Map<String, Object> params(Object... keysAndValues) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put((String) keysAndValues[i], keysAndValues[i + 1]);
        }
        return map;
    }
}

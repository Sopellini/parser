package com.recruitment.task.parser.service;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InputReadingServiceTest {

    private String parsedInput;

    @Mock
    private ParsingService parsingService;

    @InjectMocks
    private InputReadingService inputReadingService;


    @BeforeEach
    void setUp() throws IOException {
        parsedInput = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "parsedInput.txt")));
    }


    @Test
    public void shouldReadInputText() throws IOException {
        // given
        String input = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "input.txt")));
        InputStream stream = new ByteArrayInputStream((input + "\n\n").getBytes(StandardCharsets.UTF_8));
        System.setIn(stream);

        // when
        String readInput = inputReadingService.readInput();

        // then
        assertThat(parsedInput).isEqualTo(readInput);
    }

    @Test
    public void shouldReadParsingOption() {
        // given
        int input = 1;
        InputStream stream = new ByteArrayInputStream((input + "\n\n").getBytes(StandardCharsets.UTF_8));
        System.setIn(stream);

        // when
        inputReadingService.parseInput(parsedInput);

        // then
        verify(parsingService, times(1)).parseTextToXml(any());
    }

}
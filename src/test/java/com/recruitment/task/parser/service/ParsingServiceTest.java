package com.recruitment.task.parser.service;

import com.recruitment.task.parser.model.Sentence;
import com.recruitment.task.parser.model.Text;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ParsingServiceTest {
    private static final Fixtures fixtures = new Fixtures();

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ParsingService parsingService = new ParsingService();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void shouldParseTextToXml() throws IOException {
        // given
        String outputXml = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "outputXml.txt")));

        // when
        parsingService.parseTextToXml(fixtures.textToParse);

        // then
        assertThat(outputXml).isEqualTo(outputStreamCaptor.toString().trim());
    }

    @Test
    void shouldParseTextToCsv() throws IOException {
        // given
        String outputCsv = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "outputCsv.txt")));

        // when
        parsingService.parseTextToCsv(fixtures.textToParse);

        // then
        assertThat(outputCsv).isEqualTo(outputStreamCaptor.toString().trim());
    }

    private static class Fixtures {
        List<String> wordsList1 = List.of("a", "had", "lamb", "little", "Mary");
        List<String> wordsList2 = List.of("Aesop", "and", "called", "came", "for", "Peter", "the", "wolf");
        List<Sentence> sentences = List.of(new Sentence(wordsList1), new Sentence(wordsList2));
        Text textToParse = new Text(sentences);
    }
}
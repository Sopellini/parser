package com.recruitment.task.parser.service;

import com.recruitment.task.parser.model.Sentence;
import com.recruitment.task.parser.model.Text;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InputReadingService {

    private static final String PROVIDE_INPUT_TEXT = "\nPlease provide text to be parsed and hit Enter key (twice).";
    private static final String CHOOSE_PARSER_TEXT = "\nParse to XML (1) or to CSV (2) ? Confirm with Enter key.";
    private static final String NO_SUCH_OPTION_TEXT = "There is no such option to choose.";
    private static final String INPUT_ERROR_TEXT = "Error while reading input occured.";

    private final ParsingService parsingService;

    @Autowired
    public InputReadingService(ParsingService parsingService) {
        this.parsingService = parsingService;
    }

    public String readInput() {
        String line;
        System.out.println(PROVIDE_INPUT_TEXT);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (isNotEmpty(line = bufferedReader.readLine())) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            System.out.println(INPUT_ERROR_TEXT);
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void parseInput(String inputToParse) {
        System.out.println(CHOOSE_PARSER_TEXT);
        Text textToParse = createTextToParse(inputToParse);
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (option != 1 && option != 2) {
            option = scanner.nextInt();
            if (option == 1) {
                parsingService.parseTextToXml(textToParse);
            } else if (option == 2) {
                parsingService.parseTextToCsv(textToParse);
            } else {
                System.out.println(NO_SUCH_OPTION_TEXT);
                System.out.println(CHOOSE_PARSER_TEXT);
            }
        }
    }

    private Text createTextToParse(String inputString) {
        List<String> inputSentences = Stream.of(inputString.split("\\.")).collect(Collectors.toList());
        List<Sentence> sentences = new ArrayList<>();
        for (String sentence : inputSentences) {
            List<String> words = Arrays.stream(sentence.split(" "))
                    .filter(this::isWord)
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());
            sentences.add(new Sentence(words));
        }
        return new Text(sentences);
    }

    private boolean isNotEmpty(String input) {
        return !input.isEmpty();
    }

    private boolean isWord(String stringToCheck) {
        return !stringToCheck.isBlank() && !stringToCheck.equals(",");
    }

}

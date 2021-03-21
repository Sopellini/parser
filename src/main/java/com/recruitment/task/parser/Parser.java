package com.recruitment.task.parser;

import com.recruitment.task.parser.service.InputReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Parser {
    private final InputReadingService inputReadingService;

    @Autowired
    public Parser(InputReadingService inputReadingService) {
        this.inputReadingService = inputReadingService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        String inputToParse = inputReadingService.readInput();
        inputReadingService.parseInput(inputToParse);
    }
}

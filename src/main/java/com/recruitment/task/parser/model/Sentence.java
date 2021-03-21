package com.recruitment.task.parser.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public class Sentence {
    @XmlElement(name = "word")
    private final List<String> words;

    public Sentence(List<String> words){
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

}

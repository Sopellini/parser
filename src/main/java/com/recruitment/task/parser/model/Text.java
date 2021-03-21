package com.recruitment.task.parser.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Text {
    @XmlElement(name = "sentence")
    private List<Sentence> sentences;

    public Text() {}

    public Text(List<Sentence> sentences){
        this.sentences = sentences;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }


}

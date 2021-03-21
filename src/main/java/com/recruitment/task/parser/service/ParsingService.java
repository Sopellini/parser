package com.recruitment.task.parser.service;

import com.recruitment.task.parser.model.Sentence;
import com.recruitment.task.parser.model.Text;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.springframework.stereotype.Service;

@Service
public class ParsingService {

    public void parseTextToXml(Text textToParse) {
        try {
            JAXBContext context = JAXBContext.newInstance(Text.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(textToParse, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void parseTextToCsv(Text textToParse) {
        List<Sentence> sentences = textToParse.getSentences();
        int longestSentence = getMaxSentenceLength(sentences);
        List<String> headers = new ArrayList<>();
        headers.add("");
        for (int i = 1; i <= longestSentence; i++) {
            headers.add("Word " + i);
        }
        String headersToPrint = String.join(",", headers);
        List<String> rows = new ArrayList<>();
        for (int i = 1; i <= sentences.size(); i++) {
            rows.add("Sentence " +
                    i +
                    "," +
                    String.join(",", sentences.get(i - 1).getWords())
            );
        }
        System.out.println("\n" + headersToPrint);
        for (String row : rows) {
            System.out.println("\n" + row);
        }
    }

    private int getMaxSentenceLength(List<Sentence> sentences) {
        return sentences.stream()
                .mapToInt(sentence -> sentence.getWords().size())
                .max()
                .getAsInt();
    }

}
